package com.example.mobiledev.api

import com.example.mobiledev.data.CharacterDao
import com.example.mobiledev.data.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CharacterRepository(
    private val apiService: ApiService,
    private val characterDao: CharacterDao
) {
    fun getCharactersFromDb(): Flow<List<CharacterEntity>> {
        return characterDao.getAllCharacters()
    }

    suspend fun fetchCharactersFromApiAndSaveToDb() {
        withContext(Dispatchers.IO) {
            val response = apiService.getCharacters(1)
            if (response.isSuccessful) {
                response.body()?.results?.let { characters ->
                    val entities = characters.map { character ->
                        CharacterEntity(
                            name = character.name,
                            height = character.height,
                            mass = character.mass,
                            birth_year = character.birth_year,
                            gender = character.gender
                        )
                    }
                    characterDao.deleteAll()
                    characterDao.insertAll(entities)
                }
            }
        }
    }
}

