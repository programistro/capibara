package com.example.capibara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.capibara.domain.usecase.HasPinUseCase
import com.example.capibara.navigation.AppNavGraph
import com.example.capibara.navigation.Route
import com.example.capibara.ui.theme.CapibaraTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var hasPin: HasPinUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val startDestination = if (hasPin()) {
            Route.Main.route
        } else {
            Route.Auth.route
        }
        setContent {
            CapibaraTheme {
                AppNavGraph(startDestination = startDestination)
            }
        }
    }
}
