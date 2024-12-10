package com.example.mobiledev.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import com.example.mobiledev.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.File


private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val USERNAME_KEY = stringPreferencesKey("username")
    private val THEME_KEY = booleanPreferencesKey("theme")
    private val LANGUAGE_KEY = booleanPreferencesKey("language")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadSettings()

        binding.saveButton.setOnClickListener {
            saveSettings()
        }

        binding.deleteFileButton.setOnClickListener {
            deleteFile()
        }

        binding.restoreFileButton.setOnClickListener {
            restoreFile()
        }
    }

    private fun loadSettings() {
        val username = readFromDataStore(USERNAME_KEY) ?: ""
        binding.usernameEditText.setText(username)

        binding.themeSwitch.isChecked = readFromDataStore(THEME_KEY) ?: false
        binding.languageSwitch.isChecked = readFromDataStore(LANGUAGE_KEY) ?: false
    }

    private fun saveSettings() {
        val username = binding.usernameEditText.text.toString()
        saveToDataStore(USERNAME_KEY, username)

        val themeEnabled = binding.themeSwitch.isChecked
        val languageEnabled = binding.languageSwitch.isChecked

        saveToDataStore(THEME_KEY, themeEnabled)
        saveToDataStore(LANGUAGE_KEY, languageEnabled)
        Toast.makeText(requireContext(), "Settings saved", Toast.LENGTH_SHORT).show()
    }

    private fun deleteFile() {
        val file = File(requireContext().getExternalFilesDir(null), "dmitriikubarev.txt")
        if (file.exists()) {
            file.delete()
            Toast.makeText(requireContext(), "File deleted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "File not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun restoreFile() {
        val backupFile = File(requireContext().filesDir, "dmitriikubarev_backup.txt")
        val publicFile = File(requireContext().getExternalFilesDir(null), "dmitriikubarev.txt")

        if (backupFile.exists()) {
            backupFile.copyTo(publicFile, overwrite = true)
            Toast.makeText(requireContext(), "File restored", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "No backup found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun <T> readFromDataStore(key: androidx.datastore.preferences.core.Preferences.Key<T>): T? {
        return runBlocking {
            val preferences = requireContext().dataStore.data.first()
            preferences[key]
        }
    }

    private fun <T> saveToDataStore(
        key: androidx.datastore.preferences.core.Preferences.Key<T>,
        value: T
    ) {
        runBlocking {
            requireContext().dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
