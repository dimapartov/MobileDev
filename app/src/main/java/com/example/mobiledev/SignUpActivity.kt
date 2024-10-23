/*package com.example.mobiledev

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        emailInput = findViewById(R.id.signup_email_input)
        passwordInput = findViewById(R.id.signup_password_input)
        val signUpButton: Button = findViewById(R.id.sign_up_button)

        signUpButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            // Передаем email и пароль в Intent и открываем SignInActivity
            val intent = Intent(this, SignInActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            startActivity(intent)  // Запускаем SignInActivity
            finish()  // Закрываем SignUpActivity
        }
    }
}*/
package com.example.mobiledev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    private val TAG = "SignUpActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        emailInput = findViewById(R.id.signup_email_input)
        passwordInput = findViewById(R.id.signup_password_input)
        val signUpButton: Button = findViewById(R.id.sign_up_button)

        signUpButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            // Создаем объект User и передаем его через Intent
            val user = User(email, password)
            val intent = Intent(this, SignInActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
            finish() // Закрываем SignUpActivity и возвращаемся в SignInActivity
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
        // Активность становится видимой для пользователя
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
        // Активность начинает взаимодействовать с пользователем
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
        // Активность больше не на переднем плане (пользователь переключился на другую активность)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
        // Активность больше не видна пользователю
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart called")
        // Активность перезапускается после остановки
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
        // Активность уничтожается, освобождаются ресурсы
    }
}
