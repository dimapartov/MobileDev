package com.example.mobiledev

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    // Фиктивные данные для входа
    private val DUMMY_EMAIL = "test@example.com"
    private val DUMMY_PASSWORD = "password123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        val signInButton: Button = findViewById(R.id.sign_in_button)

        signInButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (validateInput(email, password)) {
                // Проверка фиктивных данных
                if (email == DUMMY_EMAIL && password == DUMMY_PASSWORD) {
                    val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Валидация полей
    private fun validateInput(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            emailInput.error = "Email is required"
            return false
        }
        if (TextUtils.isEmpty(password)) {
            passwordInput.error = "Password is required"
            return false
        }
        return true
    }
}