package com.example.mobiledev.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiledev.api.CharacterViewModel
import com.example.mobiledev.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private val viewModel: CharacterViewModel by viewModels()
    private val characterAdapter = CharacterAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = characterAdapter
        }

        binding.nextButton.setOnClickListener { viewModel.nextPage() }
        binding.previousButton.setOnClickListener { viewModel.previousPage() }

        viewModel.characters.observe(viewLifecycleOwner) { characters ->
            characterAdapter.submitList(characters)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let { Log.e("CharactersFragment", "Error observed: $it") }
        }

        viewModel.nextPageEnabled.observe(viewLifecycleOwner) {
            binding.nextButton.isEnabled = it
        }

        viewModel.previousPageEnabled.observe(viewLifecycleOwner) {
            binding.previousButton.isEnabled = it
        }

        viewModel.fetchCharacters()
    }
}
