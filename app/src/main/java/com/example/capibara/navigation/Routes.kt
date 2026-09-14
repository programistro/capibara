package com.example.capibara.navigation

sealed class Route(val route: String) {
    data object Auth : Route("auth")
    data object Registration : Route("registration")
    data object Main : Route("main")
}
