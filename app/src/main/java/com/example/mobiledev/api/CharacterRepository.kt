package com.example.mobiledev.api


class CharacterRepository(private val apiService: ApiService) {

    suspend fun getCharacters(page: Int): Result<CharacterResponse> {
        return try {
            val response = apiService.getCharacters(page)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
