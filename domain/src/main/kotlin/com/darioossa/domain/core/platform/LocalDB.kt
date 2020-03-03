package com.darioossa.domain.core.platform

import com.darioossa.domain.models.Post

/**
 * interface for local database required features
 */
interface LocalDB {
    /**
     * Returns all not deleted list of [Post]
     */
    fun getPosts(): List<Post>

    /**
     * Saves the list of [Post]
     */
    fun insertOrUpdate(posts: List<Post>)

    /**
     * Saves the list of [Post] deleting previously saved posts not appearing in this new list
     */
    fun refresh(posts: List<Post>)

    /**
     * Deletes the post if exists
     */
    fun deletePost(post: Post): Post
}