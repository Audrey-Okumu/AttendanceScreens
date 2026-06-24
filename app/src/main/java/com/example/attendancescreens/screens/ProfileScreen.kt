package com.example.attendancescreens.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancescreens.components.ProfileMenuItem
import com.example.attendancescreens.components.ProfileStatItem
import com.example.attendancescreens.ui.theme.AppDarkGreen
import com.example.attendancescreens.ui.theme.AppGoldAccent
import com.example.attendancescreens.ui.theme.AppLightGray
import com.example.attendancescreens.ui.theme.AppTextGray
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(onLogout: () -> Unit, modifier: Modifier = Modifier) {
    val currentUser = remember {
        try {
            FirebaseAuth.getInstance().currentUser
        } catch (_: Exception) {
            null
        }
    }
    val userName = currentUser?.displayName ?: "User"
    val userEmail = currentUser?.email ?: "user@example.com"

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    AppDarkGreen,
                    shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Profile",
                color = White,
                fontSize = 24.sp,
                fontWeight = Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Profile Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(24.dp),
                color = White,
                tonalElevation = 8.dp,
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(AppLightGray, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Picture",
                            modifier = Modifier.size(60.dp),
                            tint = AppDarkGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = userName,
                        fontSize = 22.sp,
                        fontWeight = Bold,
                        color = Color.Black
                    )

                    Text(
                        text = "Senior Employee",
                        fontSize = 14.sp,
                        color = AppTextGray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileStatItem(icon = Icons.Default.Badge, label = "ID", value = "EMP-1024")
                        ProfileStatItem(icon = Icons.Default.Email, label = "Email", value = userEmail)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(24.dp),
                color = White,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    ProfileMenuItem(icon = Icons.Default.Settings, title = "Account Settings")
                    ProfileMenuItem(icon = Icons.Default.Notifications, title = "Notifications")
                    ProfileMenuItem(icon = Icons.Default.Shield, title = "Privacy & Security")
                    ProfileMenuItem(icon = Icons.Default.Info, title = "Help & Support")
                    
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        thickness = 0.5.dp,
                        color = AppLightGray
                    )

                    ProfileMenuItem(
                        icon = Icons.AutoMirrored.Filled.Logout,
                        title = "Logout",
                        titleColor = Color.Red,
                        showArrow = false,
                        onClick = onLogout
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPrev() {
    ProfileScreen(onLogout = {})
}
