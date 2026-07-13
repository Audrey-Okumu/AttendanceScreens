package com.example.attendancescreens.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.attendancescreens.AuthManager
import com.example.attendancescreens.model.SignUpState
import com.example.attendancescreens.model.SignUpStateType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val authManager: AuthManager) : ViewModel() {
    private val _signupState = MutableStateFlow(SignUpState())
    val signupState: StateFlow<SignUpState> = _signupState.asStateFlow()

    fun signUp() {
        viewModelScope.launch {
            _signupState.value = SignUpState(SignUpStateType.LOADING)
            val result = authManager.signUpWithGoogle()
            
            if (result.errorMessage != null) {
                _signupState.value = SignUpState(SignUpStateType.ERROR, errorMessage = result.errorMessage)
            } else {
                _signupState.value = SignUpState(
                    SignUpStateType.SIGNED_IN,
                    userData = result.userData,
                    isEmailVerified = result.isEmailVerified
                )
            }
        }
    }

    fun signUpWithEmail(name: String, email: String, password: String) {
        viewModelScope.launch {
            _signupState.value = SignUpState(SignUpStateType.LOADING)
            val result = authManager.signUpWithEmailPassword(name, email, password)
            
            if (result.errorMessage != null) {
                _signupState.value = SignUpState(SignUpStateType.ERROR, errorMessage = result.errorMessage)
            } else {
                _signupState.value = SignUpState(
                    SignUpStateType.SIGNED_IN,
                    userData = result.userData,
                    isEmailVerified = result.isEmailVerified
                )
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _signupState.value = SignUpState(SignUpStateType.LOADING)
            val result = authManager.signInWithEmailPassword(email, password)
            
            if (result.errorMessage != null) {
                _signupState.value = SignUpState(SignUpStateType.ERROR, errorMessage = result.errorMessage)
            } else {
                _signupState.value = SignUpState(
                    SignUpStateType.SIGNED_IN,
                    userData = result.userData,
                    isEmailVerified = result.isEmailVerified
                )
            }
        }
    }

    fun resetPassword(email: String) {
        if (email.isBlank()) {
            _signupState.value = SignUpState(SignUpStateType.ERROR, errorMessage = "Please enter your email address")
            return
        }
        viewModelScope.launch {
            _signupState.value = SignUpState(SignUpStateType.LOADING)
            val result = authManager.sendPasswordResetEmail(email)
            if (result.errorMessage != null) {
                _signupState.value = SignUpState(SignUpStateType.ERROR, errorMessage = result.errorMessage)
            } else {
                _signupState.value = SignUpState(SignUpStateType.SIGNED_OUT, errorMessage = "Reset email sent! Check your inbox or spam.")
            }
        }
    }

    fun clearError() {
        _signupState.value = _signupState.value.copy(stateType = SignUpStateType.SIGNED_OUT, errorMessage = null)
    }

    fun signOut() {
        viewModelScope.launch {
            authManager.signOut()
            _signupState.value = SignUpState(SignUpStateType.SIGNED_OUT)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return SignUpViewModel(AuthManager(application.applicationContext)) as T
            }
        }
    }
}
