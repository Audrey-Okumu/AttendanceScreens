package com.example.attendancescreens.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancescreens.AuthManager
import com.example.attendancescreens.model.SignUpState
import com.example.attendancescreens.model.SignUpStateType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(application : Application) : AndroidViewModel(application) {
    private val _signupState = MutableStateFlow(SignUpState())
    val signupState: StateFlow<SignUpState> = _signupState.asStateFlow()

    private val authManager by lazy { AuthManager(application.applicationContext) }

    fun signUp() {
        viewModelScope.launch {
            _signupState.value = SignUpState(SignUpStateType.LOADING)
            val result = authManager.signUpWithGoogle()
            
            if (result.errorMessage != null) {
                _signupState.value = SignUpState(SignUpStateType.ERROR)
            } else {
                _signupState.value = SignUpState(SignUpStateType.SIGNED_IN, userData = result.userData)
            }
        }
    }

    fun signUpWithEmail(name: String, email: String, password: String) {
        viewModelScope.launch {
            _signupState.value = SignUpState(SignUpStateType.LOADING)
            val result = authManager.signUpWithEmailPassword(name, email, password)
            
            if (result.errorMessage != null) {
                _signupState.value = SignUpState(SignUpStateType.ERROR)
            } else {
                _signupState.value = SignUpState(SignUpStateType.SIGNED_IN, userData = result.userData)
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _signupState.value = SignUpState(SignUpStateType.LOADING)
            val result = authManager.signInWithEmailPassword(email, password)
            
            if (result.errorMessage != null) {
                _signupState.value = SignUpState(SignUpStateType.ERROR)
            } else {
                _signupState.value = SignUpState(SignUpStateType.SIGNED_IN, userData = result.userData)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authManager.signOut()
            _signupState.value = SignUpState(SignUpStateType.SIGNED_OUT)
        }
    }
}
