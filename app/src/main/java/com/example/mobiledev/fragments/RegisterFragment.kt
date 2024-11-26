package com.example.mobiledev.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobiledev.R
import com.example.mobiledev.data.User

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

        val registerButton: Button = view.findViewById(R.id.register_register_button)

        registerButton.setOnClickListener {
            val emailInput: EditText = view.findViewById(R.id.register_email_input)
            val passwordInput: EditText = view.findViewById(R.id.register_password_input)

            val enteredEmail = emailInput.text.toString().trim()
            val enteredPassword = passwordInput.text.toString().trim()

            if (enteredEmail.isNotEmpty() && enteredPassword.isNotEmpty()) {
                val user = User(enteredEmail, enteredPassword)
                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(user)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Введите почту и пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}