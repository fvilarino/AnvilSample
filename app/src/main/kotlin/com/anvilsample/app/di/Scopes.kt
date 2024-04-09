package com.anvilsample.app.di

import javax.inject.Scope
import kotlin.reflect.KClass

abstract class AppScope private constructor()
abstract class UserScope private constructor()

@Scope
annotation class SingleIn(val scope: KClass<*>)
