package com.example.attendancescreens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ProfileAction(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onRemoveClick: (() -> Unit)? = null
) {

    if (showDialog) {

        AlertDialog(
            onDismissRequest = onDismiss,

            title = {
                Text("Change Profile Picture")
            },

            text = {
                Column {

                    TextButton(
                        onClick = onCameraClick
                    ) {
                        Text("📷 Take Photo")
                    }

                    TextButton(
                        onClick = onGalleryClick
                    ) {
                        Text("🖼️ Choose from Gallery")
                    }

                    if (onRemoveClick != null) {
                        TextButton(
                            onClick = onRemoveClick
                        ) {
                            Text("🗑️ Remove Photo")
                        }
                    }
                }
            },

            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text("Cancel")
                }
            },

            confirmButton = {}
        )
    }
}