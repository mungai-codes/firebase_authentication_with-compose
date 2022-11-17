package com.mungaicodes.composefirebaseauthentication.presentation.login

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mungaicodes.composefirebaseauthentication.util.Constants.TAG
import com.mungaicodes.composefirebaseauthentication.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                _uiState.update { state ->
                    state.copy(
                        loginSuccess = true
                    )
                }
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar("User logged In successfully")
                    )
                }
                Log.i(TAG, "The user successfully logged in.")
            } else {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar("User failed logged In: " + it.exception)
                    )
                }
                Log.i(TAG, "The user failed to login " + it.exception)
            }
        }
    }

//    fun crashlyticsTest() {
//        Log.i(TAG, "Causes a crash!")
//        throw RuntimeException("exception")
//    }

}