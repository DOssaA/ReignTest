package com.darioossa.reignandroidtest.core.platform

import android.content.Context
import com.darioossa.domain.core.platform.LocalDB
import com.darioossa.domain.models.Post
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDbImpl
@Inject constructor(): LocalDB {

    lateinit var boxStore: BoxStore
        private set

    val postBox: Box<LocalPost> by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        boxStore.boxFor(LocalPost::class)
    }

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    override fun getPosts(): List<Post> = postBox.all.filter { !it.deleted }.map { it.toPost() }

    override fun insertOrUpdate(posts: List<Post>) {
        val allSaved = postBox.all
        val toSave = posts.map { LocalPost.from(it) }
        //saves preserving deletion status
        toSave.forEach {
            val saved = allSaved.firstOrNull { post -> post.created_at == it.created_at }
            if (saved != null) {
                it.id = saved.id
                it.deleted = saved.deleted
            }
        }
        postBox.put(toSave)
    }

    override fun refresh(posts: List<Post>) {
        //to remove: only posts not received but also not saved as deleted
        val toRemove = postBox.all?.filter { saved ->
            posts.firstOrNull { toSave -> toSave.created_at == saved.created_at } == null
                    && !saved.deleted
        }
        postBox.remove(toRemove)
        insertOrUpdate(posts)
    }

    override fun deletePost(post: Post): Post {
        //sets existing post as deleted
        postBox.query().equal(LocalPost_.created_at, post.created_at).equal(LocalPost_.deleted, false)
            .build().findFirst()?.let {
            it.deleted = true
            postBox.put(it)
        }
        post.deleted = true
        return post
    }
}