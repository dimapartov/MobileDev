package com.example.mobiledev.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    val page: Int,
    @PrimaryKey val name: String,
    val height: String,
    val mass: String,
    val birth_year: String,
    val gender: String
)
