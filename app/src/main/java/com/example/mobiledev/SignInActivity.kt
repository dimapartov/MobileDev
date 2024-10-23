/*
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
    private val DUMMY_EMAIL = "1"
    private val DUMMY_PASSWORD = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        emailInput = findViewById(R.id.signin_email_input)
        passwordInput = findViewById(R.id.signin_password_input)
        val signInButton: Button = findViewById(R.id.sign_in_button)
        val goToSignUpButton: Button = findViewById(R.id.go_to_sign_up_button)

        // Проверяем, были ли переданы данные через Intent от SignUpActivity
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")

        // Автозаполнение полей если данные были переданы
        if (!email.isNullOrEmpty()) {
            emailInput.setText(email)
        }
        if (!password.isNullOrEmpty()) {
            passwordInput.setText(password)
        }

        signInButton.setOnClickListener {
            val emailText = emailInput.text.toString().trim()
            val passwordText = passwordInput.text.toString().trim()

            if (validateInput(emailText, passwordText)) {
                // Проверка фиктивных данных
                if (emailText == DUMMY_EMAIL && passwordText == DUMMY_PASSWORD) {
                    val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        goToSignUpButton.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)  // Запускаем SignUpActivity
            finish()  // Закрываем SignInActivity, чтобы вернуться после завершения SignUpActivity
        }
    }

    // Метод для проверки валидности введенных данных
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
*/
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
    private val DUMMY_EMAIL = "1"
    private val DUMMY_PASSWORD = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        emailInput = findViewById(R.id.signin_email_input)
        passwordInput = findViewById(R.id.signin_password_input)
        val signInButton: Button = findViewById(R.id.sign_in_button)
        val goToSignUpButton: Button = findViewById(R.id.go_to_sign_up_button)

        // Проверяем, был ли передан объект User через Intent
        val user = intent.getSerializableExtra("user") as? User

        // Автозаполнение полей, если объект User был передан
        user?.let {
            emailInput.setText(it.email)
            passwordInput.setText(it.password)
        }

        signInButton.setOnClickListener {
            val emailText = emailInput.text.toString().trim()
            val passwordText = passwordInput.text.toString().trim()

            if (validateInput(emailText, passwordText)) {
                // Проверка фиктивных данных
                if (emailText == DUMMY_EMAIL && passwordText == DUMMY_PASSWORD) {
                    val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        goToSignUpButton.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)  // Запускаем SignUpActivity
            finish()  // Закрываем SignInActivity
        }
    }

    // Метод для проверки валидности введенных данных
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

