package com.darioossa.domain.core.interactor

import com.darioossa.domain.core.exception.Failure
import com.darioossa.domain.core.functional.Either
import com.darioossa.domain.models.Post
import com.darioossa.domain.repository.PostsRepository
import javax.inject.Inject

/**
 * This class is a [UseCase] for deleting a [Post] given in [Params]
 */
class DeletePost
@Inject constructor(private val repository: PostsRepository): UseCase<Post, DeletePost.Params>(){

    override suspend fun run(params: Params): Either<Failure, Post> {
        return repository.deletePost(params.post)
    }

    data class Params(val post: Post)
}