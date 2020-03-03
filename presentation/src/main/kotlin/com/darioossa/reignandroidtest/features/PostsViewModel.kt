package com.darioossa.reignandroidtest.features

import androidx.lifecycle.MutableLiveData
import com.darioossa.domain.core.interactor.DeletePost
import com.darioossa.domain.core.interactor.GetPosts
import com.darioossa.domain.core.interactor.SavePosts
import com.darioossa.domain.core.interactor.UseCase
import com.darioossa.domain.models.Post
import com.darioossa.reignandroidtest.core.platform.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsViewModel
@Inject constructor(private val getPosts: GetPosts,
                    private val deletePost: DeletePost,
                    private val savePosts: SavePosts): BaseViewModel() {

    var posts = MutableLiveData<List<Post>>()
    var deletedPost = MutableLiveData<Post>()

    fun getPosts() {
        getPosts.execute({ GlobalScope.launch(Dispatchers.Main) {
            it.either(::handleFailure, ::handlePostsReceived)
        }}, UseCase.None())
    }

    fun deletePost(post: Post) {
        deletePost.execute({ GlobalScope.launch(Dispatchers.Main) {
            it.either(::handleFailure, ::handleDeleted)
        }}, DeletePost.Params(post))
    }

    fun savePosts(postList: List<Post>) {
        savePosts.execute({ GlobalScope.launch(Dispatchers.Main) {
            it.either(::handleFailure, ::handleSaved)
        }}, SavePosts.Params(postList))
    }

    private fun handlePostsReceived(postList: List<Post>) {
        posts.value = postList
    }

    private fun handleDeleted(post: Post) {
        deletedPost.value = post
    }

    private fun handleSaved(none: UseCase.None) {
        //ignored
    }

}