package com.example.mobiledev.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiledev.R
import com.example.mobiledev.api.ApiServiceProvider
import com.example.mobiledev.api.Character
import com.example.mobiledev.api.CharacterRepository
import com.example.mobiledev.data.AppDatabase
import com.example.mobiledev.databinding.FragmentCharactersBinding
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding ?: throw RuntimeException()

    private lateinit var repository: CharacterRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = AppDatabase.getInstance(requireContext()).characterDao()
        val apiService = ApiServiceProvider.createApiService()
        repository = CharacterRepository(apiService, dao)

        val adapter = CharacterAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        binding.refreshButton.setOnClickListener {
            Toast.makeText(requireContext(), "Characters loaded from API and saved to DB", Toast.LENGTH_LONG).show()
            lifecycleScope.launch {
                repository.fetchCharactersFromApiAndSaveToDb()
            }
        }

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_charactersFragment_to_settingsFragment)
        }

        observeCharacters(adapter)
    }

    private fun observeCharacters(adapter: CharacterAdapter) {
        Toast.makeText(requireContext(), "Characters loaded from DB", Toast.LENGTH_LONG).show()
        lifecycleScope.launch {
            repository.getCharactersFromDb().collect { characters ->
                adapter.submitList(characters.map {
                    Character(it.name, it.height, it.mass, it.birth_year, it.gender)
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
