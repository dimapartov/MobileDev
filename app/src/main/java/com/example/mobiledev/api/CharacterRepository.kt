package com.example.mobiledev.api

import android.content.Context
import android.util.Log
import com.example.mobiledev.data.CharacterDao
import com.example.mobiledev.data.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File

class CharacterRepository(
    private val apiService: ApiService,
    private val characterDao: CharacterDao,
    private val context: Context // Add context to handle file operations
) {
    private val TAG = "CharacterRepository"
    private val fileName = "characters.txt"

    fun getCharactersFromDb(page: Int): Flow<List<CharacterEntity>> {
        return characterDao.getCharactersByPage(page)
    }

    suspend fun fetchCharactersFromApiAndSaveToDb(page: Int) {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCharacters(page)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        Log.d(
                            TAG,
                            "Received ${body.results.size} characters from API for page $page"
                        )
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
                        saveCharactersToFile(body.results) // Save to file
                    }
                } else {
                    Log.e(TAG, "Error fetching characters: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception while fetching characters: ${e.message}")
            }
        }
    }

    private fun saveCharactersToFile(characters: List<Character>) {
        // Основной файл
        val mainFile = File(context.getExternalFilesDir(null), fileName)
        // Бэкап файл
        val backupFile = File(context.filesDir, "characters_backup.txt")

        // Сохранение в основной файл
        mainFile.printWriter().use { out ->
            characters.forEach { character ->
                out.println(
                    "Name: ${character.name}, Height: ${character.height}, " +
                            "Mass: ${character.mass}, Birth Year: ${character.birth_year}, Gender: ${character.gender}"
                )
            }
        }

        // Сохранение в бэкап файл
        backupFile.printWriter().use { out ->
            characters.forEach { character ->
                out.println(
                    "Name: ${character.name}, Height: ${character.height}, " +
                            "Mass: ${character.mass}, Birth Year: ${character.birth_year}, Gender: ${character.gender}"
                )
            }
        }

        Log.d(TAG, "Characters saved to file: ${mainFile.absolutePath}")
        Log.d(TAG, "Backup saved to file: ${backupFile.absolutePath}")
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
