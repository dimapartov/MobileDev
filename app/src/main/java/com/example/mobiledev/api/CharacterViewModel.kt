package com.example.mobiledev.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class CharacterViewModel : ViewModel() {

    private val apiService: ApiService
    private var currentPage = 1

    init {
        val json = Json {
            ignoreUnknownKeys = true // Add this to handle unknown fields in JSON
            isLenient = true
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }


    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _nextPageEnabled = MutableLiveData<Boolean>()
    val nextPageEnabled: LiveData<Boolean> get() = _nextPageEnabled

    private val _previousPageEnabled = MutableLiveData<Boolean>()
    val previousPageEnabled: LiveData<Boolean> get() = _previousPageEnabled

    fun fetchCharacters(page: Int = currentPage) {
        viewModelScope.launch {
            try {
                val response = apiService.getCharacters(page)
                if (response.isSuccessful) {
                    response.body()?.let { characterResponse ->
                        _characters.value = characterResponse.results
                        currentPage = page
                        _nextPageEnabled.value = characterResponse.next != null
                        _previousPageEnabled.value = characterResponse.previous != null
                        _error.value = null
                    }
                } else {
                    val errorMsg = "Failed to load data: ${response.code()}"
                    _error.value = errorMsg
                    Log.e("CharacterViewModel", errorMsg)
                }
            } catch (e: Exception) {
                val errorMsg = "Error: ${e.message}"
                _error.value = errorMsg
                Log.e("CharacterViewModel", errorMsg, e)
            }
        }
    }

    fun nextPage() {
        if (nextPageEnabled.value == true) fetchCharacters(currentPage + 1)
    }

    fun previousPage() {
        if (previousPageEnabled.value == true) fetchCharacters(currentPage - 1)
    }
}
