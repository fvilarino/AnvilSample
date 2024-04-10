package com.anvilsample.app.di

import com.anvilsample.app.ComponentRepository
import com.anvilsample.app.repository.User
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Provider

const val USER_COMPONENT_NAME = "user_component"

interface UserManager {
    val isLoggedIn: Flow<Boolean>
    val user: Flow<User?>

    fun login(user: User)
    fun logout()
}

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class UserManagerImpl @Inject constructor(
    private val userComponentFactory: Provider<UserComponent.Factory>,
    private val componentRepository: ComponentRepository,
) : UserManager {


    override val user: MutableStateFlow<User?> = MutableStateFlow(null)

    override val isLoggedIn = MutableStateFlow(false)

    override fun login(user: User) {
        val userComponent = userComponentFactory.get().create(user)
        componentRepository.put(userComponent, USER_COMPONENT_NAME)
        this.user.value = user
        isLoggedIn.value = true
    }

    override fun logout() {
        componentRepository.clear(USER_COMPONENT_NAME)
        isLoggedIn.value = false
        user.value = null
    }
}
