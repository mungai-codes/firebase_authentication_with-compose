package com.mungaicodes.composefirebaseauthentication.presentation.signUp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mungaicodes.composefirebaseauthentication.util.Constants
import com.mungaicodes.composefirebaseauthentication.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun createUser() {
        viewModelScope.launch(Dispatchers.IO) {
            Firebase.auth
                .createUserWithEmailAndPassword(_uiState.value.email, _uiState.value.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        viewModelScope.launch(Dispatchers.IO) {
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar("Account successfully created!")
                            )
                            Log.i(Constants.TAG, "The user successfully created an account..")
                        }
                    } else {
                        viewModelScope.launch(Dispatchers.IO) {
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar("An error when creating account." + it.exception)
                            )
                            Log.i(
                                Constants.TAG,
                                "The user failed to create account! " + it.exception
                            )

                        }
                    }
                }
        }
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

    fun clearEmailField() {
        _uiState.update {
            it.copy(
                email = ""
            )
        }
    }

}