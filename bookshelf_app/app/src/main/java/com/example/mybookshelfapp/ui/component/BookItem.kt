package com.example.mybookshelfapp.ui.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mybookshelfapp.model.ImageLinks

@Composable
fun BookItem(thumb: ImageLinks, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(thumb.thumbnail)
            .crossfade(true)
            .build(), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().aspectRatio(3f/4f))
    }
}

@Preview(showBackground = false)
@Composable
fun BookItemPreview() {
    BookItem(ImageLinks("http://books.google.com/books/publisher/content?id=1bm0DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE72XTfD0oQU9FcP2HqbZuTbKPtnKIJ_qF3zGQXKxS1Q8dnbIgS0120rnXz4kMcIVKrTJGdtQUXzvhr_VClPazS8rRiDrSAoIkEZ5L11a141kW1FsoHhFdYk2DRz-VKM8H5s6P7x8&source=gbs_api", "http://books.google.com/books/publisher/content?id=1bm0DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE72XTfD0oQU9FcP2HqbZuTbKPtnKIJ_qF3zGQXKxS1Q8dnbIgS0120rnXz4kMcIVKrTJGdtQUXzvhr_VClPazS8rRiDrSAoIkEZ5L11a141kW1FsoHhFdYk2DRz-VKM8H5s6P7x8&source=gbs_api"))
}