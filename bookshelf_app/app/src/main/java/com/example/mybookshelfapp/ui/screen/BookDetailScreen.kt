package com.example.mybookshelfapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mybookshelfapp.model.ItemsItem
import com.example.mybookshelfapp.ui.component.BookSpec
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@Composable
fun BookDetailScreen(
    uiState: BookshelfUiState<ItemsItem>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is BookshelfUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BookshelfUiState.Success -> uiState.data?.let {
            BookDetail(
                book = it,
                modifier = modifier.verticalScroll(rememberScrollState())
            )
        }

        is BookshelfUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetail(book: ItemsItem, modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = {
            Button(
                onClick = { }, modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
                formatter.currency = Currency.getInstance("IDR")
                Row {
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(
                        text = formatter.format(book.saleInfo?.listPrice?.amount),
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.LightGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = formatter.format(book.saleInfo?.retailPrice?.amount),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                }
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(book.volumeInfo?.imageLinks?.thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Book Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(250.dp)
                        .aspectRatio(3f / 5f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = book.volumeInfo?.title ?: "Unknown",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            book.volumeInfo?.authors?.let {
                BookSpec(
                    title = "Author",
                    detail = it.joinToString(", ")
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            book.volumeInfo?.categories?.joinToString(", ")
                ?.let { BookSpec(title = "Category", detail = it) }
            Spacer(modifier = Modifier.height(8.dp))
            BookSpec(title = "Publisher", detail = book.volumeInfo?.publisher ?: "Unknown")
            Spacer(modifier = Modifier.height(8.dp))
            BookSpec(title = "Published Date", detail = book.volumeInfo?.publishedDate ?: "Unknown")
            Spacer(modifier = Modifier.height(16.dp))
            BookSpec(title = "Description", detail = book.volumeInfo?.description ?: "N/A")
        }
    }
}

//@Preview
//@Composable
//BookDetailPreview() {
//    BookDetail()
//}