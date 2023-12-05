package com.example.mybookshelfapp.data

import com.example.mybookshelfapp.model.BooksResponse
import com.example.mybookshelfapp.model.ItemsItem
import com.example.mybookshelfapp.network.BookshelfService

interface BookshelfRepository {
    suspend fun getBooks(): BooksResponse

    suspend fun getDetailBook(volumeId: String): ItemsItem
}

class NetworkBookshelfRepository(
    private val bookshelfService: BookshelfService
) : BookshelfRepository {

    override suspend fun getBooks(): BooksResponse {
        return bookshelfService.getBooks()
    }

    override suspend fun getDetailBook(volumeId: String): ItemsItem {
        return bookshelfService.getBookDetail(volumeId)
    }
}