package com.darioossa.data.repository

import com.darioossa.data.net.PostsService
import com.darioossa.domain.core.exception.Failure
import com.darioossa.domain.core.exception.Failure.*
import com.darioossa.domain.core.functional.Either
import com.darioossa.domain.core.functional.Either.*
import com.darioossa.domain.core.interactor.UseCase
import com.darioossa.domain.core.platform.LocalDB
import com.darioossa.domain.models.Post
import com.darioossa.domain.core.platform.NetworkHandler
import com.darioossa.domain.entities.PostResponseEntity
import com.darioossa.domain.repository.PostsRepository
import retrofit2.Call
import javax.inject.Inject

/**
 * [PostsRepository] implementation for retrieving posts data
 */

class PostsRepositoryImpl
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: PostsService,
                        private val localDB: LocalDB): PostsRepository {

    override fun getPosts(): Either<Failure, List<Post>> {
        return try {
            when (networkHandler.isConnected()) {
                true -> {
                    val response = service.posts().execute()
                    if (response.isSuccessful) {
                        response.body()?.hits?.map { it.toPost() }?.let {
                            localDB.refresh(it)
                        }
                        Right(localDB.getPosts())
                    } else{
                        Left(ServerError)
                    }
                }
                false -> {
                    val posts = localDB.getPosts()
                    if (posts.isNullOrEmpty()) {
                        Left(NetworkConnection)
                    } else {
                        Right(posts)
                    }
                }
            }
        } catch (exception: Throwable) {
            Left(ServerError)
        }
    }

    override fun SavePost(posts: List<Post>): Either<Failure, UseCase.None> {
        return try {
            localDB.insertOrUpdate(posts)
            Right(UseCase.None())
        } catch (exception: Throwable) {
            Left(ServerError)
        }
    }

    override fun deletePost(post: Post): Either<Failure, Post> {
        return try {
            Right(localDB.deletePost(post))
        } catch (exception: Throwable) {
            Left(ServerError)
        }
    }


    /**
     * Handles api request ([call]) response to let the [transform] function to execute on success
     * or else the [default] one
     */
    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Right(transform((response.body() ?: default)))
                false -> Left(ServerError)
            }
        } catch (exception: Throwable) {
            Left(ServerError)
        }
    }

}