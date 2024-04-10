package com.anvilsample.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.anvilsample.app.di.USER_COMPONENT_NAME
import com.anvilsample.app.di.UserComponent
import com.anvilsample.app.repository.DummyRepository
import com.anvilsample.app.repository.User
import com.anvilsample.app.repository.UserRepository
import javax.inject.Inject

class TwoFragment : Fragment() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var dummyRepository: DummyRepository

    @Inject
    lateinit var user: User

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val userComponent = requireNotNull(
            (context.applicationContext as ComponentRepository)
                .get<UserComponent>(USER_COMPONENT_NAME)
        )
        userComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.user).text =
            "Logged in as: $user, magic number: ${dummyRepository.getNumber()}"
        view.findViewById<TextView>(R.id.details).text =
            userRepository.getUserDetails(id = user.id).toString()
        view.findViewById<Button>(R.id.back).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
