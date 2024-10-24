package com.example.mobiledev.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobiledev.R
import com.example.mobiledev.activities.MainActivity

class LoginFragment : Fragment() {

    private val DUMMY_EMAIL = "1"
    private val DUMMY_PASSWORD = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailInput: EditText = view.findViewById(R.id.login_email_input)
        val passwordInput: EditText = view.findViewById(R.id.login_password_input)

        arguments?.let {
            val email = it.getString("email")
            val password = it.getString("password")
            emailInput.setText(email)
            passwordInput.setText(password)
        }

        val signInButton: Button = view.findViewById(R.id.login_login_button)
        signInButton.setOnClickListener {
            val enteredEmail = emailInput.text.toString()
            val enteredPassword = passwordInput.text.toString()

            if (enteredEmail == DUMMY_EMAIL && enteredPassword == DUMMY_PASSWORD) {
                (activity as MainActivity).navigateToHome()
            } else {
                Toast.makeText(context, "Неверный email или пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

