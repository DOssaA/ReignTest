package com.darioossa.data.net

import com.darioossa.domain.entities.PostResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * interface that represents the api for getting a [PostResponseEntity]
 */

internal interface PostsApi {

    companion object {
        private const val POSTS = "/api/v1/search_by_date"
        private const val ANDROID_QUERY = "android"
    }

    @GET(POSTS) fun posts(@Query("query") query: String = ANDROID_QUERY): Call<PostResponseEntity>
}