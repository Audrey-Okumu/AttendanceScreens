package com.example.attendancescreens.ui.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val context = getApplication<Application>().applicationContext
    
    private val _photoUrl = MutableStateFlow<Uri?>(firebaseAuth.currentUser?.photoUrl)
    val photoUrl: StateFlow<Uri?> = _photoUrl.asStateFlow()

    private val _userName = MutableStateFlow(firebaseAuth.currentUser?.displayName ?: "User")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val authStateListener = FirebaseAuth.AuthStateListener { auth ->
        val user = auth.currentUser
        _photoUrl.value = user?.photoUrl
        _userName.value = user?.displayName ?: "User"
    }

    init {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onCleared() {
        super.onCleared()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

    fun updateProfilePhoto(uri: Uri) {
        viewModelScope.launch {
            try {
                // Copy the image to internal storage so it survives app restarts
                val permanentUri = saveImageToInternalStorage(uri) ?: return@launch
                
                val user = firebaseAuth.currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setPhotoUri(permanentUri)
                    .build()
                
                user?.updateProfile(profileUpdates)?.await()
                // Force local sync by reloading the user info from Firebase
                user?.reload()?.await()

                // Update local state flows to trigger immediate UI recomposition
                // We append a timestamp to the URI as a "cache buster" so Coil knows to reload it
                val updatedUser = firebaseAuth.currentUser
                val updatedUri = updatedUser?.photoUrl?.let {
                    it.buildUpon().appendQueryParameter("t", System.currentTimeMillis().toString()).build()
                }
                _photoUrl.value = updatedUri
                _userName.value = updatedUser?.displayName ?: "User"

                // Log success
                android.util.Log.d("ProfileViewModel", "Profile photo updated successfully: $permanentUri")
            } catch (e: Exception) {
                android.util.Log.e("ProfileViewModel", "Error updating profile photo", e)
            }
        }
    }

    fun deleteProfilePhoto() {
        viewModelScope.launch {
            try {
                val user = firebaseAuth.currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setPhotoUri(null)
                    .build()

                user?.updateProfile(profileUpdates)?.await()
                user?.reload()?.await()

                // Clean up local storage
                val filesDir = context.filesDir
                filesDir.listFiles { _, name -> name.startsWith("profile_picture_") }?.forEach { it.delete() }

                // Update local state flows
                val updatedUser = firebaseAuth.currentUser
                _photoUrl.value = updatedUser?.photoUrl

                android.util.Log.d("ProfileViewModel", "Profile photo removed successfully")
            } catch (e: Exception) {
                android.util.Log.e("ProfileViewModel", "Error removing profile photo", e)
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            
            // Clean up old profile pictures
            val filesDir = context.filesDir
            filesDir.listFiles { _, name -> name.startsWith("profile_picture_") }?.forEach { it.delete() }
            
            // Create a unique filename for the new picture to bust caches
            val filename = "profile_picture_${System.currentTimeMillis()}.jpg"
            val file = File(filesDir, filename)
            val outputStream = FileOutputStream(file)
            
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            Uri.fromFile(file)
        } catch (e: Exception) {
            android.util.Log.e("ProfileViewModel", "Error saving image to internal storage", e)
            null
        }
    }
}
