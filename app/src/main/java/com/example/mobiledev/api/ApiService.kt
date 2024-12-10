package com.example.mobiledev.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("people/")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharacterResponse>
}
