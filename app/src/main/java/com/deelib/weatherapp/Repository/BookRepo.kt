package com.deelib.weatherapp.Repository

import com.deelib.weatherapp.model.ApiResult
import com.deelib.weatherapp.model.Item
import com.deelib.weatherapp.network.ApiService
import com.deelib.weatherapp.network.RemoteSource
import com.deelib.weatherapp.network.RetrofitClient
import com.deelib.weatherapp.network.RetrofitClient.apiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookRepo() {
    val remoteSource = RemoteSource()
    fun books(searchWord: String): Flow<ApiResult<List<Item>>> = flow {
        emit(ApiResult.Loading)
        emit(remoteSource.getBooks(searchWord))
    }

}