package com.example.mobiledev.fragments

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding ?: throw RuntimeException()
    private lateinit var repository: CharacterRepository
    private val adapter = CharacterAdapter()
    private var currentPage = 1
    private val TAG = "CharactersFragment"

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
            clearDb()
        }

        binding.previousButton.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                loadCharacters(currentPage)
            } else {
                Toast.makeText(requireContext(), "Вы на первой странице", Toast.LENGTH_SHORT).show()
            }
        }

        binding.nextButton.setOnClickListener {
            if (currentPage < 9) { // Поправьте в зависимости от максимальной страницы
                currentPage++
                loadCharacters(currentPage)
            } else {
                Toast.makeText(requireContext(), "Вы на последней странице", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        loadCharacters(currentPage)
    }

    private fun refreshPage(page: Int) {
        lifecycleScope.launch {
            try {
                repository.fetchCharactersFromApiAndSaveToDb(page)
                delay(100) // Ждем перед загрузкой из базы
                loadCharactersFromDb(page)
                Toast.makeText(
                    requireContext(),
                    "Данные обновлены из API для страницы $page",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при обновлении данных: ${e.message}")
                Toast.makeText(
                    requireContext(),
                    "Ошибка при обновлении данных из API",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loadCharacters(page: Int) {
        lifecycleScope.launch {
            if (repository.isPageInDatabase(page)) {
                loadCharactersFromDb(page)
                Toast.makeText(
                    requireContext(),
                    "Загрузка из БД для страницы $page",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                try {
                    repository.fetchCharactersFromApiAndSaveToDb(page)
                    loadCharactersFromDb(page)
                    Toast.makeText(
                        requireContext(),
                        "Загрузка из API для страницы $page",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Log.e(TAG, "Ошибка загрузки персонажей из API: ${e.message}")
                    Toast.makeText(requireContext(), "Ошибка загрузки из API", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun clearDb() {
        lifecycleScope.launch {
            repository.clearDb()
            Toast.makeText(requireContext(), "Данные очищены", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadCharactersFromDb(page: Int) {
        lifecycleScope.launch {
            repository.getCharactersFromDb(page).collect { characters ->
                if (characters.isNotEmpty()) {
                    adapter.submitList(characters.map {
                        Character(it.name, it.height, it.mass, it.birth_year, it.gender)
                    })
                } else {
                    adapter.submitList(emptyList())
                    Toast.makeText(
                        requireContext(),
                        "Нет данных для страницы $page",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
