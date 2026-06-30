package com.example.attendancescreens.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancescreens.components.OnboardingGif
import com.example.attendancescreens.ui.theme.AttendanceScreensTheme

@Composable
fun LaunchScreen(onSignupClick: () -> Unit ,onLoginClick: () -> Unit ,modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ){
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                                androidx.compose.ui.graphics.Color.Transparent
                            )
                        ),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                OnboardingGif()
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "HR CONNECT",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = Bold,
                    letterSpacing = 4.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Your workplace, simplified.",
                style = MaterialTheme.typography.bodyLarge.copy(letterSpacing = 1.sp),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(80.dp))

            Button(
                onClick = onSignupClick,
                modifier = Modifier.fillMaxWidth().height(58.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(18.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    "CREATE ACCOUNT",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = Bold, letterSpacing = 1.sp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth().height(58.dp),
                shape = RoundedCornerShape(18.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    "SIGN IN",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = Bold, letterSpacing = 1.sp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LaunchScreenPrev(){
    LaunchScreen(onSignupClick = {},onLoginClick = {})
}

@Preview(showBackground = true)
@Composable
private fun LaunchScreenPrevDark() = AttendanceScreensTheme(darkTheme = true){
    LaunchScreen(onSignupClick = {},onLoginClick = {})
}