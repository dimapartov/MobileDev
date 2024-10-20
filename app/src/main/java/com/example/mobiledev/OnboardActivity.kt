package com.example.mobiledev

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class OnboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)

        val welcomeButton: Button = findViewById(R.id.onboard_welcome_button)

        // Переход к экрану входа
        welcomeButton.setOnClickListener {
            val intent = Intent(this@OnboardActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}