package com.example.capibara.presentation.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.capibara.domain.model.Article
import com.example.capibara.domain.usecase.GetArticleUseCase
import com.example.capibara.navigation.NewsDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface NewsDetailUiState {
    data object Loading : NewsDetailUiState
    data object NotFound : NewsDetailUiState
    data class Content(val article: Article) : NewsDetailUiState
}

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getArticle: GetArticleUseCase
) : ViewModel() {

    private val route = savedStateHandle.toRoute<NewsDetailRoute>()

    val uiState: StateFlow<NewsDetailUiState> = getArticle(route.articleId)
        .map { article ->
            if (article == null) {
                NewsDetailUiState.NotFound
            } else {
                NewsDetailUiState.Content(article)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NewsDetailUiState.Loading
        )
}
