package com.example.mobiledev

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var chatListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        chatListView = findViewById(R.id.chat_list_view)

        val chatData = listOf(
            "John Doe: Hey! How's it going?",
            "Jane Smith: Don't forget the meeting tomorrow.",
            "Alice Brown: Can you send me the files?",
            "Bob Johnson: I'll be there in 10 minutes."
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, chatData)
        chatListView.adapter = adapter
    }
}