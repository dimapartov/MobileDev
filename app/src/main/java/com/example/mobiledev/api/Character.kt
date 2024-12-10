package com.example.mobiledev.api

import kotlinx.serialization.Serializable

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