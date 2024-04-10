package com.anvilsample.app

interface ComponentRepository {
    fun<T : Any> get(key: String): T?
    fun put(component: Any, key: String)
    fun clear(key: String)
}
