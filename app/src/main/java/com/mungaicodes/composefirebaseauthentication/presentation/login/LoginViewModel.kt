package com.mungaicodes.composefirebaseauthentication.presentation.login

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val isEmailValid = derivedStateOf {
        Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()
    }

    private val isPasswordValid = derivedStateOf {
        _uiState.value.password.length > 7
    }

    private val auth by lazy {
        Firebase.auth
    }

    fun onEmailChange(newValue: String) {
        _uiState.update {
            it.copy(
                email = newValue,
            )
        }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update {
            it.copy(
                password = newValue
            )
        }
    }

    fun checkEmailValidity() {
        if (isEmailValid.value) {
            _uiState.update {
                it.copy(
                    isEmailValid = true,
                )
            }
        }
    }

    fun checkPasswordValidity() {
        if (isPasswordValid.value) {
            _uiState.update {
                it.copy(
                    isPasswordValid = true,
                )
            }
        }
    }

    fun clearEmailField() {
        _uiState.update {
            it.copy(
                email = ""
            )
        }
    }

    fun login() {
        auth.signInWithEmailAndPassword(
            _uiState.value.email,
            _uiState.value.password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i(TAG, "The user successfully logged in.")
            } else {
                Log.i(TAG, "The user failed to login " + it.exception)
            }
        }
    }

    companion object {
        const val TAG: String = "Mungai"
    }
}