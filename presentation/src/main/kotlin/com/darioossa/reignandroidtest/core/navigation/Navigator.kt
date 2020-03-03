package com.darioossa.reignandroidtest.core.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.darioossa.domain.models.Post
import com.darioossa.reignandroidtest.R
import com.darioossa.reignandroidtest.core.platform.BaseFragment
import com.darioossa.reignandroidtest.features.PostsActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor() {

    fun showMain(context: Context) = context.startActivity(PostsActivity.callingIntent(context))

    fun showPostDetails(fragment: BaseFragment, post: Post) {
        //TODO: better way to open a webpage, using helper or something better
        //context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(post.story_url ?: post.url)))

        val uri = post.story_url ?: post.url
        val context = fragment.context
        if (uri != null && context != null) {
            val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            val customTabsIntent: CustomTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(uri))
        } else {
            fragment.notify(R.string.unable_to_load_post)
        }

    }
}


