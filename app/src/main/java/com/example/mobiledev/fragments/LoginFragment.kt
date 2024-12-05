package com.example.mobiledev.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobiledev.R
import com.example.mobiledev.data.User
import com.example.mobiledev.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val DUMMY_EMAIL = "1"
    private val DUMMY_PASSWORD = "1"
    private val args: LoginFragmentArgs by navArgs()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding ?: throw RuntimeException()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user: User? = args.user

        user?.let {
            binding.loginEmailInput.setText(it.email)
            binding.loginPasswordInput.setText(it.password)
        }

        binding.loginLoginButton.setOnClickListener {
            val enteredEmail = binding.loginEmailInput.text.toString()
            val enteredPassword = binding.loginPasswordInput.text.toString()

            if (enteredEmail.isNotEmpty() && enteredPassword.isNotEmpty()) {

                if (enteredEmail == DUMMY_EMAIL && enteredPassword == DUMMY_PASSWORD) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    Toast.makeText(context, "Неверный email или пароль", Toast.LENGTH_SHORT).show()
                }

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
