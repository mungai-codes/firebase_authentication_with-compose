package com.mungaicodes.composefirebaseauthentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.mungaicodes.composefirebaseauthentication.presentation.navigation.Navigation
import com.mungaicodes.composefirebaseauthentication.ui.theme.ComposeFirebaseAuthenticationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFirebaseAuthenticationTheme {
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()

                Navigation(navController = navController)
            }
        }
    }
}
