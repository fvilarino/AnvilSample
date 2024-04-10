package com.anvilsample.app.repository

import com.anvilsample.app.di.AppScope
import com.anvilsample.app.di.SingleIn
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

interface DummyRepository {
    fun getNumber(): Int
}

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class DummyRepositoryImpl @Inject constructor() : DummyRepository {
    override fun getNumber(): Int {
        return 42
    }
}
