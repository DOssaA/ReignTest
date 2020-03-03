
package com.darioossa.reignandroidtest.core.di

import com.darioossa.reignandroidtest.AndroidApp
import com.darioossa.reignandroidtest.core.di.viewmodel.ViewModelModule
import com.darioossa.reignandroidtest.features.PostsFragment
import com.darioossa.reignandroidtest.core.navigation.RouteActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApp)
    fun inject(routeActivity: RouteActivity)
    fun inject(postsFragment: PostsFragment)
}
