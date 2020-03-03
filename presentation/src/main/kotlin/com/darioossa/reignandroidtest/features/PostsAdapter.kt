package com.darioossa.reignandroidtest.features

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darioossa.domain.models.Post
import com.darioossa.reignandroidtest.R
import com.darioossa.reignandroidtest.core.extension.inflate
import com.darioossa.reignandroidtest.core.platform.DateUtil
import kotlinx.android.synthetic.main.row_post.view.*
import javax.inject.Inject
import kotlin.properties.Delegates


class PostsAdapter
@Inject constructor() : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    internal var collection: MutableList<Post> by Delegates.observable(mutableListOf()) {
            _, _, _ -> notifyDataSetChanged()
    }

    internal var clickListener: (Post) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.row_post))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post, clickListener: (Post) -> Unit) {
            itemView.postTitle.text = post.story_title ?: post.title
            itemView.postSubtitle.text = itemView.context.getString(R.string.postSub, post.author,
                DateUtil.format(itemView.context,post.created_at))
            itemView.setOnClickListener { clickListener(post) }
        }
    }
}
