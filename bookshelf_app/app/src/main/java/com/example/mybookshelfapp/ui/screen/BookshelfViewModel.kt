package com.example.mybookshelfapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mybookshelfapp.BookshelfApplication
import com.example.mybookshelfapp.data.BookshelfRepository
import com.example.mybookshelfapp.model.ItemsItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class BookshelfViewModel(
    private val bookshelfRepository: BookshelfRepository,
) : ViewModel() {
    private val _uiStateBooks = MutableStateFlow<BookshelfUiState<List<ItemsItem>>>(BookshelfUiState.Loading)
    val uiStateBooks: StateFlow<BookshelfUiState<List<ItemsItem>>> = _uiStateBooks.asStateFlow()
    private val _uiStateDetail = MutableStateFlow<BookshelfUiState<ItemsItem>>(BookshelfUiState.Loading)
    val uiStateDetail: StateFlow<BookshelfUiState<ItemsItem>> = _uiStateDetail.asStateFlow()

    init {
        getBooks()
    }

    fun getBooks() {
        viewModelScope.launch {
            try {
                _uiStateBooks.value = BookshelfUiState.Loading
                bookshelfRepository.getBooks()
                    .let { books ->
                        _uiStateBooks.value = BookshelfUiState.Success(books.items)
                    }
            } catch (e: IOException) {
                _uiStateBooks.value = BookshelfUiState.Error
            } catch (e: retrofit2.HttpException) {
                _uiStateBooks.value = BookshelfUiState.Error
            }
        }
    }

    fun getBookDetail(volumeId: String){
        viewModelScope.launch {
            try {
                _uiStateDetail.value = BookshelfUiState.Loading
                bookshelfRepository.getDetailBook(volumeId)
                    .let { bookDetail ->
                        _uiStateDetail.value = BookshelfUiState.Success(bookDetail)
                    }
            } catch (e: IOException) {
                _uiStateDetail.value = BookshelfUiState.Error
            } catch (e: retrofit2.HttpException) {
                _uiStateDetail.value = BookshelfUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookshelfViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}

sealed class BookshelfUiState<out T: Any?> {
    data class Success<out T: Any>(val data: T?) : BookshelfUiState<T>()
    object Error : BookshelfUiState<Nothing>()
    object Loading : BookshelfUiState<Nothing>()
}