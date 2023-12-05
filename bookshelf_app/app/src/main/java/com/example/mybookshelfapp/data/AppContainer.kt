package com.example.mybookshelfapp.data

import com.example.mybookshelfapp.network.BookshelfService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val bookshelfRepository: BookshelfRepository
}

class DefaultAppContainer: AppContainer {
    private val BASE_URL = "https://www.googleapis.com"

    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    private val retrofitService: BookshelfService by lazy {
        retrofit.create(BookshelfService::class.java)
    }

    override val bookshelfRepository: BookshelfRepository
        get() = NetworkBookshelfRepository(retrofitService)
}