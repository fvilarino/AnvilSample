package com.anvilsample.app

import android.app.Application
import com.anvilsample.app.di.ApplicationComponent
import com.anvilsample.app.di.DaggerApplicationComponent
import com.anvilsample.app.di.UserComponent

class AnvilApp : Application(), ComponentRepository {

    private val components = mutableMapOf<String, Any>()
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(this).apply {
            inject(this@AnvilApp)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(key: String): T? = components[key] as? T

    override fun put(component: Any, key: String) {
        components[key] = component
    }

    override fun clear(key: String) {
        components.remove(key)
    }
}
