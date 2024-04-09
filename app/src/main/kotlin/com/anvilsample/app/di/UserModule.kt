package com.anvilsample.app.di

import com.anvilsample.app.TwoFragment
import com.anvilsample.app.repository.User
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.BindsInstance
import dagger.Subcomponent

@MergeSubcomponent(UserScope::class)
@SingleIn(UserScope::class)
interface UserComponent {
    val user: User
    fun inject(oneFragment: TwoFragment)

    @Subcomponent.Factory
    fun interface Factory {
        fun create(@BindsInstance user: User): UserComponent
    }
}
