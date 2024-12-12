package com.example.mobiledev.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters WHERE page = :page")
    fun getCharactersByPage(page: Int): Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT COUNT(*) FROM characters WHERE page = :page")
    fun getCountByPage(page: Int): Int

    @Query("DELETE FROM characters")
    fun deleteAll()
}

