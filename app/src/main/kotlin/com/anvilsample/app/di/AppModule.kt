package com.anvilsample.app.di

import android.app.Application
import android.content.Context
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module

@Module
@ContributesTo(AppScope::class)
abstract class AppModule {
    @Binds
    @ApplicationContext
    @SingleIn(AppScope::class)
    abstract fun provideApplicationContext(application: Application): Context
}
