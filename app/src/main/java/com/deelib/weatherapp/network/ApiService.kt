package com.deelib.weatherapp.network

import com.deelib.weatherapp.model.BooksList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //    @GET("volumes?q=quilting")
    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String): Response<BooksList>
}