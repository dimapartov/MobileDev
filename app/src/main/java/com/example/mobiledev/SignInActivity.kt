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

    private val DUMMY_EMAIL = "1"
    private val DUMMY_PASSWORD = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        emailInput = findViewById(R.id.signin_email_input)
        passwordInput = findViewById(R.id.signin_password_input)
        val signInButton: Button = findViewById(R.id.sign_in_button)
        val goToSignUpButton: Button = findViewById(R.id.go_to_sign_up_button)

        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")

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
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    private val TAG = "SignInActivity"

    private val DUMMY_EMAIL = "1"
    private val DUMMY_PASSWORD = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        Log.d(TAG, "onCreate called")

        emailInput = findViewById(R.id.signin_email_input)
        passwordInput = findViewById(R.id.signin_password_input)
        val signInButton: Button = findViewById(R.id.sign_in_button)
        val goToSignUpButton: Button = findViewById(R.id.go_to_sign_up_button)

        val user = intent.getSerializableExtra("user") as? User

        user?.let {
            emailInput.setText(it.email)
            passwordInput.setText(it.password)
        }

        signInButton.setOnClickListener {
            val emailText = emailInput.text.toString().trim()
            val passwordText = passwordInput.text.toString().trim()

            if (validateInput(emailText, passwordText)) {
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

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

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

