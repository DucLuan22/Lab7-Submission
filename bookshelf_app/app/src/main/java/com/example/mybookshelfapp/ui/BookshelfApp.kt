package com.example.mybookshelfapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mybookshelfapp.R
import com.example.mybookshelfapp.ui.screen.BookDetailScreen
import com.example.mybookshelfapp.ui.screen.BooksScreen
import com.example.mybookshelfapp.ui.screen.BookshelfViewModel

enum class MyBookshelfScreen(val section: Int) {
    BOOKS(section = R.string.app_name),
    DETAIL(section = R.string.detail_section)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(
    navController: NavHostController = rememberNavController(),
    viewModel: BookshelfViewModel = viewModel(
        factory = BookshelfViewModel.Factory
    ),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MyBookshelfScreen.valueOf(
        backStackEntry?.destination?.route ?: MyBookshelfScreen.BOOKS.name
    )

    Scaffold(
        topBar = {
            BookshelfAppTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = MyBookshelfScreen.BOOKS.name,
            modifier = Modifier.padding(it)
        ) {
            composable(MyBookshelfScreen.BOOKS.name) {
                BooksScreen(
                    uiState = viewModel.uiStateBooks.collectAsState().value,
                    retryAction = viewModel::getBooks,
                    onClick = { book ->
                        navController.navigate(MyBookshelfScreen.DETAIL.name)
                        viewModel.getBookDetail(book)
                    }
                )
            }
            composable(MyBookshelfScreen.DETAIL.name) {
                BookDetailScreen(
                    uiState = viewModel.uiStateDetail.collectAsState().value,
                    retryAction = { })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfAppTopBar(
    currentScreen: MyBookshelfScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(currentScreen.section), fontWeight = FontWeight.Bold) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        },
        modifier = modifier,
    )
}