package com.darioossa.domain.repository

import com.darioossa.domain.core.exception.Failure
import com.darioossa.domain.core.functional.Either
import com.darioossa.domain.core.interactor.UseCase
import com.darioossa.domain.models.Post

interface PostsRepository {
    /**
     * Gets a list of [Post] from remote storage if there is internet connection or local if there are saved
     */
    fun getPosts(): Either<Failure, List<Post>>

    /**
     * Saves a list of [Post]
     */
    fun SavePost(posts: List<Post>): Either<Failure, UseCase.None>

    /**
     * Deletes the current [Post]
     */
    fun deletePost(post: Post): Either<Failure, Post>
}