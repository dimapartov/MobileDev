package com.example.mobiledev.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.mobiledev.R
import com.example.mobiledev.activities.MainActivity

class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailInput: EditText = view.findViewById(R.id.register_email_input)
        val passwordInput: EditText = view.findViewById(R.id.register_password_input)
        val registerButton: Button = view.findViewById(R.id.register_register_button)

        registerButton.setOnClickListener {
            val enteredEmail = emailInput.text.toString()
            val enteredPassword = passwordInput.text.toString()

            val args = Bundle()
            args.putString("email", enteredEmail)
            args.putString("password", enteredPassword)

            val loginFragment = LoginFragment()
            loginFragment.arguments = args

            (activity as MainActivity).navigateToLogin(loginFragment)
        }
    }
}

