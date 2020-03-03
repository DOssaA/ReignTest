package com.darioossa.data.net

import com.darioossa.domain.entities.PostResponseEntity
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * [PostsApi] implementation for getting memories data from the network
 */

class PostsService
@Inject constructor(retrofit: Retrofit) : PostsApi {
    private val moviesApi by lazy { retrofit.create(PostsApi::class.java) }

    override fun posts(query: String): Call<PostResponseEntity> =
        moviesApi.posts()
}