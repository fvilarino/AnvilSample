package com.anvilsample.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.anvilsample.app.repository.User
import com.anvilsample.app.di.UserManager
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import javax.inject.Inject

class OneFragment : Fragment() {

    @Inject
    lateinit var userManager: UserManager

    private lateinit var usernameContainer: View
    private lateinit var passwordContainer: View
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var navigateButton: Button
    private var isLoggedIn = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = (context.applicationContext as AnvilApp).appComponent
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        usernameContainer = view.findViewById(R.id.username_container)
        passwordContainer = view.findViewById(R.id.password_container)
        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)
        loginButton = view.findViewById(R.id.login)
        navigateButton = view.findViewById(R.id.navigate)

        lifecycleScope.launch {
            userManager.user.collect { user ->
                view.findViewById<TextView>(R.id.status).text = if (user != null) {
                    "Logged in as ${user.name}"
                } else {
                    "Logged out"
                }
            }
        }
        lifecycleScope.launch {
            userManager.isLoggedIn.collect { isLoggedIn ->
                this@OneFragment.isLoggedIn = isLoggedIn
                usernameContainer.isInvisible = isLoggedIn
                passwordContainer.isInvisible = isLoggedIn
                loginButton.isEnabled = isLoggedIn
                navigateButton.isVisible = isLoggedIn
                loginButton.text = if (isLoggedIn) {
                    "Logout"
                } else {
                    "Login"
                }
                loginButton.setOnClickListener {
                    if (isLoggedIn) {
                        userManager.logout()
                        loginButton.text = "Login"
                    } else {
                        userManager.login(User(id = 1, name = username.text.toString()))
                        loginButton.text = "Logout"
                        username.text?.clear()
                        password.text?.clear()
                        loginButton.isEnabled = true
                    }
                }
            }
        }

        username.doAfterTextChanged { text ->
            loginButton.isEnabled = isLoggedIn  || text?.isNotBlank() == true && password.text?.isNotBlank() == true
        }
        password.doAfterTextChanged { text ->
            loginButton.isEnabled = isLoggedIn || text?.isNotBlank() == true && username.text?.isNotBlank() == true
        }

        navigateButton.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, TwoFragment(), "two_fragment")
                .addToBackStack("two_fragment")
                .commit()
        }
    }
}
