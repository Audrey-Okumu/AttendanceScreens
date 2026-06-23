package com.example.attendancescreens.model

data class SignUpState(
    val stateType: SignUpStateType = SignUpStateType.SIGNED_OUT,
    val userData: UserData? = null
)

enum class SignUpStateType {
    SIGNED_IN, SIGNED_OUT, LOADING, ERROR
}