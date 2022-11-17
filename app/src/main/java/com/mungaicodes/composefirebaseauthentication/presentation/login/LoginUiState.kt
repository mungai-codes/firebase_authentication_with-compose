package com.mungaicodes.composefirebaseauthentication.presentation.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val loginSuccess: Boolean = false
)
