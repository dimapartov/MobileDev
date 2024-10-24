package com.example.mobiledev.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.mobiledev.R
import com.example.mobiledev.activities.MainActivity

class LoginRegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val goToSignInButton: Button = view.findViewById(R.id.go_to_sign_in_button)
        val goToSignUpButton: Button = view.findViewById(R.id.go_to_sign_up_button)

        goToSignInButton.setOnClickListener {
            (activity as MainActivity).navigateToLogin()
        }

        goToSignUpButton.setOnClickListener {
            (activity as MainActivity).navigateToRegister()
        }
    }

}