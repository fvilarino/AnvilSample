package com.anvilsample.app

import android.app.Application
import com.anvilsample.app.di.ApplicationComponent
import com.anvilsample.app.di.DaggerApplicationComponent
import com.anvilsample.app.repository.User
import com.anvilsample.app.di.UserComponent
import javax.inject.Inject

class AnvilApp : Application() {
    lateinit var appComponent: ApplicationComponent

    @Inject
    lateinit var userComponentFactory: UserComponent.Factory

    val userComponent: UserComponent
        get() = requireNotNull(_userComponent)

    private var _userComponent: UserComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(this).apply {
            inject(this@AnvilApp)
        }
    }

    fun createUserComponent(user: User) {
        check(_userComponent == null) { "Already created" }
        _userComponent = userComponentFactory.create(user)
    }

    fun releaseUserComponent() {
        check(_userComponent != null) { "Already released" }
        _userComponent = null
    }
}
