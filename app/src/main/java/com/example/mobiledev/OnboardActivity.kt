package com.example.mobiledev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class OnboardActivity : AppCompatActivity() {

    private val TAG = "OnboardActivity"

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