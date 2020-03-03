package com.darioossa.domain.entities

import com.darioossa.domain.models.Post

data class PostEntity (private val created_at: String,
                       private val title: String?,
                       private val url: String?,
                       private val story_id: Int,
                       private val story_title: String?,
                       private val story_url: String?,
                       private val author: String,
                       private var deleted: Boolean = false
    ){

    fun toPost() = Post(created_at, title, url, story_id, story_title, story_url, author, deleted)
}