package com.example.capibara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.capibara.di.DaggerAppComponent
import com.example.capibara.navigation.AppNavGraph
import com.example.capibara.navigation.Route
import com.example.capibara.ui.theme.CapibaraTheme

class MainActivity : ComponentActivity() {

    private val appComponent by lazy { DaggerAppComponent.factory().create(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val startDestination = if (appComponent.hasPinUseCase()()) {
            Route.Main.route
        } else {
            Route.Auth.route
        }
        setContent {
            CapibaraTheme {
                AppNavGraph(
                    appComponent = appComponent,
                    startDestination = startDestination
                )
            }
        }
    }
}
