package com.darioossa.reignandroidtest.core.platform

import com.darioossa.domain.models.Post
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity public class LocalPost (@Id var id: Long,
                                var created_at: String,
                                 var title: String?,
                                 var url: String?,
                                 var story_id: Int,
                                 var story_title: String?,
                                 var story_url: String?,
                                 var author: String,
                                 var deleted: Boolean = false
) {
    fun toPost() = Post(created_at, title, url, story_id, story_title, story_url, author, deleted)

    companion object {
        fun from(post: Post) = LocalPost(0, post.created_at, post.title, post.url, post.story_id,
            post.story_title, post.story_url, post.author, post.deleted)
    }
}