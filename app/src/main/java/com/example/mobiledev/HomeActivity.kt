package com.example.mobiledev

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var chatListView: ListView

    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Log.d(TAG, "onCreate called")

        chatListView = findViewById(R.id.chat_list_view)
        // Фиктивные данные чатов
        val chatData = listOf(
            "John Doe: Hey! How's it going?",
            "Jane Smith: Don't forget the meeting tomorrow.",
            "Alice Brown: Can you send me the files?",
            "Bob Johnson: I'll be there in 10 minutes."
        )
        // Адаптер для отображения списка
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, chatData)
        chatListView.adapter = adapter
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