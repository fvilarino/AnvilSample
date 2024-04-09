package com.anvilsample.app.di

import android.content.Context
import com.anvilsample.app.AnvilApp
import com.anvilsample.app.repository.User
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

interface UserManager {
    val isLoggedIn: Flow<Boolean>
    val user: Flow<User?>

    fun login(user: User)
    fun logout()
}

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class UserManagerImpl @Inject constructor(
    @ApplicationContext context: Context
) : UserManager {

    private val anvilApp = context as AnvilApp

    override val user: MutableStateFlow<User?> = MutableStateFlow(null)

    override val isLoggedIn = MutableStateFlow(false)

    override fun login(user: User) {
        anvilApp.createUserComponent(user)
        this.user.value = user
        isLoggedIn.value = true
    }

    override fun logout() {
        anvilApp.releaseUserComponent()
        isLoggedIn.value = false
        user.value = null
    }
}
