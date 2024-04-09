package com.anvilsample.app.repository

import com.anvilsample.app.di.SingleIn
import com.anvilsample.app.di.UserScope
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

data class UserDetails(
    val id: Int,
    val name: String,
    val memberSince: Long,
)

interface UserRepository {
    fun getUserDetails(id: Int): UserDetails
}

@ContributesBinding(UserScope::class)
@SingleIn(UserScope::class)
class UserRepositoryImpl @Inject constructor() : UserRepository {

    override fun getUserDetails(id: Int) = UserDetails(
        id = id,
        name = "John Doe",
        memberSince = 1234L,
    )
}
