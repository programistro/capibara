package com.example.capibara.navigation

import kotlinx.serialization.Serializable

sealed class Route(val route: String) {
    data object Auth : Route("auth")
    data object Registration : Route("registration")
    data object Main : Route("main")
}

@Serializable
data class NewsDetailRoute(val articleId: String)
