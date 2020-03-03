package com.darioossa.domain.core.interactor

import com.darioossa.domain.core.exception.Failure
import com.darioossa.domain.core.functional.Either
import com.darioossa.domain.models.Post
import com.darioossa.domain.repository.PostsRepository
import javax.inject.Inject

class SavePosts
@Inject constructor(private val repository: PostsRepository): UseCase<UseCase.None, SavePosts.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return repository.SavePost(params.posts)
    }

    data class Params(val posts: List<Post>)
}