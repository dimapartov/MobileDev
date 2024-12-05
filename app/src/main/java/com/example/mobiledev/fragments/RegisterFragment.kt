package com.example.mobiledev.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobiledev.data.User
import com.example.mobiledev.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerRegisterButton.setOnClickListener {
            val enteredEmail = binding.registerEmailInput.text.toString().trim()
            val enteredPassword = binding.registerPasswordInput.text.toString().trim()

            if (enteredEmail.isNotEmpty() && enteredPassword.isNotEmpty()) {
                val user = User(enteredEmail, enteredPassword)
                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(user)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Введите почту и пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
