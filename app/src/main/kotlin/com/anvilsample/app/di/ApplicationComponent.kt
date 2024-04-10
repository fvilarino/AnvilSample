package com.anvilsample.app.di

import android.app.Application
import com.anvilsample.app.AnvilApp
import com.anvilsample.app.ComponentRepository
import com.anvilsample.app.MainActivity
import com.anvilsample.app.OneFragment
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
interface ApplicationComponent {

    val userManager: UserManager
    fun inject(application: AnvilApp)
    fun inject(mainActivity: MainActivity)
    fun inject(oneFragment: OneFragment)


    @Component.Factory
    fun interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}

@Module
@ContributesTo(AppScope::class)
object ComponentRepositoryModule {
    @SingleIn(AppScope::class)
    @Provides
    fun provideUserComponentRepository(application: Application): ComponentRepository {
        return application as AnvilApp
    }
}

@ContributesTo(AppScope::class)
interface ParentUserComponent {
    val userComponentFactory: UserComponent.Factory
}
