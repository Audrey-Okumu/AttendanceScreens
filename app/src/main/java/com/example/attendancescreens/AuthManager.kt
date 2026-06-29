package com.example.attendancescreens

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.GetCredentialRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import com.example.attendancescreens.model.UserData
import com.example.attendancescreens.model.AuthResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthManager(private val context : Context) {
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val credentialManager: CredentialManager by lazy { CredentialManager.create(context) }
    private val getGoogleIdOption: GetGoogleIdOption by lazy {
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("78576521283-4tbfnvgo7g316jt311qcg6tg06f96jj3.apps.googleusercontent.com")
            .build()
    }
    private val getCredentialRequest: GetCredentialRequest by lazy {
        GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption)
            .build()
    }

    suspend fun signUpWithEmailPassword(name: String, email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            user?.updateProfile(profileUpdates)?.await()
            
            AuthResult(userData = UserData(userName = user?.displayName, userEmail = user?.email))
        } catch (e: Exception) {
            AuthResult(errorMessage = e.localizedMessage ?: "Sign up failed")
        }
    }

    suspend fun signInWithEmailPassword(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            AuthResult(userData = UserData(userName = user?.displayName, userEmail = user?.email))
        } catch (e: Exception) {
            AuthResult(errorMessage = e.localizedMessage ?: "Login failed")
        }
    }

    suspend fun signUpWithGoogle(): AuthResult {
        try {
            val credential =
                credentialManager.getCredential(context, getCredentialRequest).credential

            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken

                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                firebaseAuth.signInWithCredential(firebaseCredential).await()

                googleIdTokenCredential.apply {
                    val userData = UserData(userName = displayName, userEmail = id)
                    val authResult = AuthResult(userData)
                    return authResult
                }
            } else {
                return AuthResult(errorMessage = "Invalid Credentials")
            }
        } catch (exception: Exception) {
            return AuthResult(errorMessage = exception.toString())
        }

    }
    suspend fun signOut() {
        firebaseAuth.signOut()
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }
}
