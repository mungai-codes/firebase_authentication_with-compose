package com.mungaicodes.composefirebaseauthentication.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mungaicodes.composefirebaseauthentication.presentation.login.LoginScreen
import com.mungaicodes.composefirebaseauthentication.presentation.signUp.SignUpScreen
import com.mungaicodes.composefirebaseauthentication.ui.theme.Purple200

@Composable
fun Navigation(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = "home") {

        composable(route = "home") {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Purple200
            ) {
                LoginScreen(navController = navController)
            }
        }

        composable(route = "signup") {
            SignUpScreen()
        }
    }

}
