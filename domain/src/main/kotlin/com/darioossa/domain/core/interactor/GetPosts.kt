package com.darioossa.domain.core.interactor

import com.darioossa.domain.core.exception.Failure
import com.darioossa.domain.repository.PostsRepository
import com.darioossa.domain.core.functional.Either
import com.darioossa.domain.models.Post
import javax.inject.Inject

/**
 * This class is a [UseCase] for retrieving a list of [Post]
 */

class GetPosts
    @Inject constructor(private val repository: PostsRepository): UseCase<List<Post>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Post>> {
        return repository.getPosts()
    }
}