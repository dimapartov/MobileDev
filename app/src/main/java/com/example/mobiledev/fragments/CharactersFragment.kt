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
import com.example.mobiledev.databinding.FragmentCharactersBinding
import kotlinx.coroutines.launch
import java.io.File

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding ?: throw RuntimeException()
    private val characterAdapter = CharacterAdapter()
    private val repository = CharacterRepository(ApiServiceProvider.createApiService())
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = characterAdapter
        }

        binding.nextButton.setOnClickListener { fetchCharacters(currentPage + 1) }
        binding.previousButton.setOnClickListener { fetchCharacters(currentPage - 1) }
        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_charactersFragment_to_settingsFragment)
        }

        fetchCharacters(currentPage)
    }

    private fun fetchCharacters(page: Int) {
        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            val result = repository.getCharacters(page)
            binding.progressBar.visibility = View.GONE

            result.onSuccess { response ->
                characterAdapter.submitList(response.results)
                currentPage = page
                binding.nextButton.isEnabled = response.next != null
                binding.previousButton.isEnabled = response.previous != null

                // Сохраняем персонажей в файл после успешного получения
                saveCharactersToFile(response.results)

            }.onFailure { error ->
                Toast.makeText(
                    requireContext(),
                    "Error: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun saveCharactersToFile(characters: List<Character>) {
        try {
            // Получаем директорию Downloads
            val downloadDir = requireContext().getExternalFilesDir(null)
            val file = File(downloadDir, "dmitriikubarev.txt")

            // Создаем форматированную строку с информацией о персонажах
            val content = characters.joinToString(separator = "\n") { character ->
                "Name: ${character.name}\n" +
                        "Height: ${character.height}\n" +
                        "Mass: ${character.mass}\n" +
                        "Birth Year: ${character.birth_year}\n" +
                        "Gender: ${character.gender}\n" +
                        "------------------------"
            }

            // Записываем в файл
            file.writeText(content)

            // Создаем резервную копию во внутреннем хранилище
            val backupFile = File(requireContext().filesDir, "dmitriikubarev_backup.txt")
            file.copyTo(backupFile, overwrite = true)

            Toast.makeText(
                requireContext(),
                "Characters saved to file",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Error saving file: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
