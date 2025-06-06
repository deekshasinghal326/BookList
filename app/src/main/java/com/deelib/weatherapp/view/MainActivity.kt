package com.deelib.weatherapp.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentManager
import coil3.compose.AsyncImage
import com.deelib.weatherapp.Repository.BookRepo
import com.deelib.weatherapp.RepositoryHolder
import com.deelib.weatherapp.model.ApiResult
import com.deelib.weatherapp.model.Item
import com.deelib.weatherapp.ui.theme.WeatherAppTheme
import com.deelib.weatherapp.viewmodel.BooksViewModel


class MainActivity : AppCompatActivity() {
    private val booksViewModel by viewModels<BooksViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        RepositoryHolder.bookRepo = BookRepo()
        booksViewModel.getBooks()
        setContent {
            WeatherAppTheme {
                HomePage(booksViewModel, supportFragmentManager)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(viewModel: BooksViewModel, supportFragmentManager: FragmentManager) {
    val state by viewModel.booksData.collectAsState()
    var selectedBook by remember { mutableStateOf<Item?>(null) }

    when (val result = state) {
        is ApiResult.Success -> {
            BookList(result.data) { clickedBook ->
                selectedBook = clickedBook
                supportFragmentManager.let { fm ->
                    DetailsFragment.newInstance(clickedBook).show(fm, "DetailsFragment")
                }
            }

        }

        is ApiResult.Error -> {
            Text(text = "Error: ${result.message}")
        }

        is ApiResult.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()

            }
        }
    }
}

@Composable
fun BookList(items: List<Item>, onItemClick: (Item) -> Unit = {}) {
    LazyColumn(contentPadding = PaddingValues(top = 50.dp)) {
        itemsIndexed(items) { index, item ->
            BookItem(item, index, onItemClick)
            if (index < items.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
fun BookItem(item: Item, index: Int = 0, onItemClick: (Item) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onItemClick(item) }
    ) {
        val imageUrl = item.volumeInfo.imageLinks.thumbnail.replace("http://", "https://")
        AsyncImage(
            model = imageUrl,
            contentDescription = "book image",
            modifier = Modifier
                .size(60.dp)
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = "${index + 1}. ${item.volumeInfo.title}",
                fontSize = 16.sp, fontWeight = FontWeight.Bold
            )
            Text(
                text = item.volumeInfo.authors.firstOrNull() ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }

}





