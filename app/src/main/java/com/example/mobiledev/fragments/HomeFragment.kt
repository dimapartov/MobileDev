package com.example.mobiledev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    private lateinit var chatListView: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        chatListView = view.findViewById(R.id.home_chat_list)

        // Чат
        val chatData = listOf(
            "John Doe: Hey! How's it going?",
            "Jane Smith: Don't forget the meeting tomorrow.",
            "Alice Brown: Can you send me the files?",
            "Bob Johnson: I'll be there in 10 minutes."
        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, chatData)
        chatListView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {}

}
