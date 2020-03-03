package com.darioossa.domain.models

data class Post(val created_at: String,
                val title: String?,
                val url: String?,
                val story_id: Int,
                val story_title: String?,
                val story_url: String?,
                val author: String,
                var deleted: Boolean
                )