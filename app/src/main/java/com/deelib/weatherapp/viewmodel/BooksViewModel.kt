package com.deelib.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deelib.weatherapp.Repository.BookRepo
import com.deelib.weatherapp.RepositoryHolder
import com.deelib.weatherapp.model.ApiResult
import com.deelib.weatherapp.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BooksViewModel : ViewModel() {
    val repo = RepositoryHolder.bookRepo

    private val _booksData = MutableStateFlow<ApiResult<List<Item>>>(ApiResult.Loading)
    val booksData : StateFlow<ApiResult<List<Item>>> = _booksData

    fun getBooks() {
        viewModelScope.launch {
            repo.books().collect {
                _booksData.value = it
            }
        }

    }

}