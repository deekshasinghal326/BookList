package com.deelib.weatherapp.network

import com.deelib.weatherapp.model.ApiResult
import com.deelib.weatherapp.model.Item

class RemoteSource {
    val apiService: ApiService = RetrofitClient.apiService

    suspend fun getBooks(): ApiResult<List<Item>> {
        val response = apiService.getBooks()
        try {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return ApiResult.Success(body.items)
                }
            } else {
                return ApiResult.Error(response.message())
            }
            return ApiResult.Error("Something went wrong")
        } catch (e: Exception) {
            return ApiResult.Error(e.message.toString())
        }
    }
}