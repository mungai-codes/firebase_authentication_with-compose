package com.mungaicodes.composefirebaseauthentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.mungaicodes.composefirebaseauthentication.presentation.login.LoginScreen
import com.mungaicodes.composefirebaseauthentication.ui.theme.ComposeFirebaseAuthenticationTheme
import com.mungaicodes.composefirebaseauthentication.ui.theme.Purple200
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFirebaseAuthenticationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Purple200
                ) {
                    LoginScreen()
                }
            }
        }
    }
}
