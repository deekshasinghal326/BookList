package com.deelib.weatherapp.network

import com.deelib.weatherapp.model.BooksList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("volumes?q=quilting")
    suspend fun getBooks(): Response<BooksList>
}