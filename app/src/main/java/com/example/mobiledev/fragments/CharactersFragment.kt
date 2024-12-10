package com.example.mobiledev.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiledev.api.ApiServiceProvider
import com.example.mobiledev.api.CharacterRepository
import com.example.mobiledev.databinding.FragmentCharactersBinding
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding ?: throw RuntimeException()
    private val characterAdapter = CharacterAdapter()


    private val repository = CharacterRepository(ApiServiceProvider.createApiService())

    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
            }.onFailure { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

