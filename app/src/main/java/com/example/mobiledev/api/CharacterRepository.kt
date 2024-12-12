package com.example.mobiledev.api

import com.example.mobiledev.data.CharacterDao
import com.example.mobiledev.data.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import android.util.Log

class CharacterRepository(
    private val apiService: ApiService,
    private val characterDao: CharacterDao
) {
    private val TAG = "CharacterRepository"

    fun getCharactersFromDb(page: Int): Flow<List<CharacterEntity>> {
        return characterDao.getCharactersByPage(page)
    }

    suspend fun fetchCharactersFromApiAndSaveToDb(page: Int) {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCharacters(page)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        Log.d(TAG, "Received ${body.results.size} characters from API for page $page")
                        val entities = body.results.map { character ->
                            CharacterEntity(
                                page = page,
                                name = character.name,
                                height = character.height,
                                mass = character.mass,
                                birth_year = character.birth_year,
                                gender = character.gender
                            )
                        }
                        characterDao.insertAll(entities)
                    }
                } else {
                    Log.e(TAG, "Error fetching characters: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception while fetching characters: ${e.message}")
            }
        }
    }

    suspend fun isPageInDatabase(page: Int): Boolean {
        return withContext(Dispatchers.IO) {
            characterDao.getCountByPage(page) > 0
        }
    }

    suspend fun clearDb() {
        return withContext(Dispatchers.IO) {
            characterDao.deleteAll()
            Log.d(TAG, "Database cleared")
        }
    }
}
