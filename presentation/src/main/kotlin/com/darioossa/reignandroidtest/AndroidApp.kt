package com.darioossa.reignandroidtest

import android.app.Application
import com.darioossa.domain.core.platform.LocalDB
import com.darioossa.reignandroidtest.core.di.ApplicationComponent
import com.darioossa.reignandroidtest.core.di.ApplicationModule
import com.darioossa.reignandroidtest.core.di.DaggerApplicationComponent
import com.darioossa.reignandroidtest.core.platform.LocalDbImpl
import javax.inject.Inject

/**
 * Created by Dario Mauricio Ossa Arias on 1/07/2018.
 * dario.ossa.a@gmail.com
 */

class AndroidApp: Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    @Inject lateinit var localDbImpl: LocalDbImpl

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        localDbImpl.init(this)
    }

    private fun injectMembers() = appComponent.inject(this)

}