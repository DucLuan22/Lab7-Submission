package com.example.mybookshelfapp.ui.screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mybookshelfapp.R
import com.example.mybookshelfapp.model.ItemsItem
import com.example.mybookshelfapp.ui.component.BookItem

@Composable
fun BooksScreen(uiState: BookshelfUiState<List<ItemsItem>>, retryAction: () -> Unit, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    when (uiState) {
        is BookshelfUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BookshelfUiState.Success -> uiState.data?.let {
            BookList(
                books = it,
                modifier = modifier.padding(4.dp),
                onClick = onClick
            )
        }

        is BookshelfUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier)
    }
}

@Composable
fun BookList(
    books: List<ItemsItem>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(books) { book ->
            BookItem(
                thumb = book.volumeInfo?.imageLinks!!,
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 2.dp).clickable {
                    onClick(book.id.toString())
                }
            )
            Log.d(TAG, "BookList: ${book.volumeInfo.imageLinks.thumbnail}")
        }
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(imageVector = Icons.Filled.Warning, contentDescription = null)
        Text(text = stringResource(id = R.string.failed_load), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BooksScreenPreview() {
//    BooksScreen(uiState = BookshelfUiState.Success(listOf()), retryAction = {})
//}