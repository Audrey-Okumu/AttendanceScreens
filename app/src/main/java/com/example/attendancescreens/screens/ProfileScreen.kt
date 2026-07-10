package com.example.attendancescreens.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.attendancescreens.components.ProfileAction
import com.example.attendancescreens.components.ProfileMenuItem
import com.example.attendancescreens.components.StatCard
import com.example.attendancescreens.ui.theme.AppMediumGreen
import com.example.attendancescreens.ui.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File

val TAG: String = "PROFILE SCREEN"

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val photoUrl by viewModel.photoUrl.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userEmail = remember { FirebaseAuth.getInstance().currentUser?.email ?: "user@example.com" }

    var showDialog by remember {
        mutableStateOf(false)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                viewModel.updateProfilePhoto(uri)
            }
        }

    val imageFile = remember {
        File(
            context.cacheDir,
            "profile_photo_${System.currentTimeMillis()}.jpg"
        )
    }

    val imageUri = remember(imageFile) {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        )
    }

    var cameraImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val takePictureLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success ->
            if (success && cameraImageUri != null) {
                viewModel.updateProfilePhoto(cameraImageUri!!)
            }
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {

                cameraImageUri = imageUri

                takePictureLauncher.launch(imageUri)
            }

        }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                // Background Curve with Gradient
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        AppMediumGreen
                                    )
                                )
                            )
                    )
                }

                // Profile Image and Info - Positioned to overlap the curve bottom
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier
                            .clickable { showDialog = true }
                            .size(80.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        shadowElevation = 6.dp,
                        border = androidx.compose.foundation.BorderStroke(3.dp, MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        if (photoUrl != null) {
                            AsyncImage(
                                model = photoUrl,
                                contentDescription = "Profile",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                error = rememberVectorPainter(Icons.Default.Person),
                                placeholder = rememberVectorPainter(Icons.Default.Person)
                            )
                        } else {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "profile",
                                    modifier = Modifier.size(48.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold, fontSize = 20.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    
                    Text(
                        text = "Senior Software Engineer",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Stats Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Badge,
                    label = "Employee ID",
                    value = "EMP-2024"
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Email,
                    label = "Work Email",
                    value = userEmail.split("@").first() + "@..."
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Menu Section
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 2.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    ProfileMenuItem(icon = Icons.Default.Settings, title = "Account Settings")
                    ProfileMenuItem(icon = Icons.Default.Notifications, title = "Notifications")
                    ProfileMenuItem(icon = Icons.Default.Shield, title = "Privacy & Security")
                    ProfileMenuItem(icon = Icons.Default.Info, title = "Help & Support")
                    
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    ProfileMenuItem(
                        icon = Icons.AutoMirrored.Filled.Logout,
                        title = "Logout",
                        titleColor = MaterialTheme.colorScheme.error,
                        showArrow = false,
                        onClick = onLogout
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }

    ProfileAction(
        showDialog = showDialog,
        onDismiss = {
            showDialog = false
        },
        onGalleryClick = {
            galleryLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        },
        onCameraClick = {
            showDialog = false
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPrev() {
    ProfileScreen(onLogout = {})
}
