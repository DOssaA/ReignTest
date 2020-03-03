package com.darioossa.reignandroidtest.features

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.darioossa.domain.core.exception.Failure
import com.darioossa.domain.models.Post
import com.darioossa.reignandroidtest.R
import com.darioossa.reignandroidtest.core.extension.observe
import com.darioossa.reignandroidtest.core.extension.failure
import com.darioossa.reignandroidtest.core.extension.viewModel
import com.darioossa.reignandroidtest.core.navigation.Navigator
import com.darioossa.reignandroidtest.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_posts.*
import javax.inject.Inject


/**
 * Fragment that shows a list of memories
 */

class PostsFragment: BaseFragment() {
    override fun layoutId(): Int = R.layout.fragment_posts

    private lateinit var viewModel: PostsViewModel

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        viewModel = viewModel(viewModelFactory) {
            observe(posts, ::handlePosts)
            observe(deletedPost, ::handleDeletion)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadPosts()
    }

    private fun initializeView() {
        configureRecyclerView()
        pullToRefresh.setOnRefreshListener {
            viewModel.getPosts()
        }
    }

    private fun loadPosts() {
        viewModel.getPosts()
    }

    private fun handlePosts(posts: List<Post>?) {
        postsAdapter.collection = posts?.toMutableList() ?: mutableListOf()
        pullToRefresh.isRefreshing = false
    }

    private fun handleDeletion(post: Post?) {
        notify(R.string.info_deleted_post)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
        }
        pullToRefresh.isRefreshing = false
    }

    private fun configureRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        recyclerview.layoutManager = layoutManager
        postsAdapter.clickListener = { post ->
            navigator.showPostDetails(this, post)
        }
        recyclerview.adapter = postsAdapter
        val itemTouchHelperCallback = PostSwipeCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        itemTouchHelperCallback.swipeListener = { position ->
            viewModel.deletePost(postsAdapter.collection[position])
            postsAdapter.collection.removeAt(position)
            postsAdapter.notifyDataSetChanged()
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerview)
    }

    private fun renderFailure(@StringRes message: Int) {
        notifyWithAction(message, R.string.action_refresh, ::loadPosts)
    }
}