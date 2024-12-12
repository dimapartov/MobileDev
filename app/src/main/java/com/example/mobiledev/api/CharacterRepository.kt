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
    fun getCharactersFromDb(page: Int): Flow<List<CharacterEntity>> {
        return characterDao.getCharactersByPage(page)
    }

    suspend fun fetchCharactersFromApiAndSaveToDb(page: Int) {
        withContext(Dispatchers.IO) {
            val response = apiService.getCharacters(page)
            if (response.isSuccessful) {
                response.body()?.results?.let { characters ->
                    val entities = characters.map { character ->
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
            }
        }
    }

    suspend fun isPageInDatabase(page: Int): Boolean {
        return withContext(Dispatchers.IO) {
            characterDao.getCountByPage(page) > 0
        }
    }
}
