package com.deelib.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deelib.weatherapp.RepositoryHolder
import com.deelib.weatherapp.model.ApiResult
import com.deelib.weatherapp.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BooksViewModel : ViewModel() {
    val repo = RepositoryHolder.bookRepo

    private val _booksData = MutableStateFlow<ApiResult<List<Item>>>(ApiResult.Loading)
    val booksData: StateFlow<ApiResult<List<Item>>> = _booksData

    private val _selectedBook = MutableStateFlow<Item?>(null)
    val selectedBook: StateFlow<Item?> = _selectedBook

    fun setSelectedBook(book: Item) {
        _selectedBook.value = book
    }

    fun clearSelection() {
        _selectedBook.value = null
    }

    fun getBooks() {
        viewModelScope.launch {
            repo.books().collect {
                _booksData.value = it
            }
        }

    }

}