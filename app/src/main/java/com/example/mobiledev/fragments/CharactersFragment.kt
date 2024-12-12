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
    private val adapter = CharacterAdapter()

    private var currentPage = 1

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

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        binding.refreshButton.setOnClickListener {
            refreshPage(currentPage)
        }

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_charactersFragment_to_settingsFragment)
        }

        binding.previousButton.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                loadCharactersForPage(currentPage)
            }
        }

        binding.nextButton.setOnClickListener {
            currentPage++
            loadCharactersForPage(currentPage)
        }

        loadCharactersForPage(currentPage)
    }


    private fun refreshPage(currentPage: Int) {
        lifecycleScope.launch {
            repository.fetchCharactersFromApiAndSaveToDb(currentPage)
            loadCharactersFromDb(currentPage)
            Toast.makeText(requireContext(), "Data refreshed from API", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadCharactersForPage(page: Int) {
        lifecycleScope.launch {
            val dbHasData = repository.isPageInDatabase(page)
            if (dbHasData) {
                loadCharactersFromDb(page)
                Toast.makeText(requireContext(), "Data from DB", Toast.LENGTH_SHORT).show()
            } else {
                repository.fetchCharactersFromApiAndSaveToDb(page)
                loadCharactersFromDb(page)
                Toast.makeText(requireContext(), "Data from api", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadCharactersFromDb(page: Int) {
        lifecycleScope.launch {
            repository.getCharactersFromDb(page).collect { characters ->
                if (characters.isNotEmpty()) {
                    adapter.submitList(characters.map {
                        Character(it.name, it.height, it.mass, it.birth_year, it.gender)
                    })
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
