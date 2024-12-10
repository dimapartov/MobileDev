package com.example.mobiledev.api

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


@Serializable
data class Character(
    val name: String,
    val height: String,
    val mass: String,
    val birth_year: String,
    val gender: String
)

@Serializable
data class CharacterResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Character>
)

interface ApiService {
    @GET("people/")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharacterResponse>
}
