package com.example.attendancescreens.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancescreens.R
import com.example.attendancescreens.ui.theme.AppDarkGreen
import com.example.attendancescreens.ui.theme.AppGoldAccent
import com.example.attendancescreens.ui.theme.AttendanceScreensTheme

@Composable
fun LaunchScreen(onSignupClick: () -> Unit ,onClick: () -> Unit,modifier: Modifier = Modifier) {
    val splash = painterResource(R.drawable.splash)
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ){
            Image(
                painter = splash,
                contentDescription = "Splash image",
                modifier = Modifier
                    .size(220.dp)
            )
            Text(
                text = "HR CONNECT",
                fontWeight = Bold,
                fontSize = 40.sp
            )
            Spacer(modifier.height(100.dp))
            Button(
                onClick = onSignupClick,
                modifier = Modifier.width(350.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppDarkGreen),
                shape = RoundedCornerShape(36.dp)
            ) {
                Text(
                    "Sign Up",
                    fontSize = 18.sp,
                    fontWeight = Bold,
                    color = androidx.compose.ui.graphics.Color.White
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {},
                modifier = Modifier.width(350.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppGoldAccent),
                shape = RoundedCornerShape(36.dp)
            ) {
                Text(
                    "Log In",
                    fontSize = 18.sp,
                    fontWeight = Bold,
                    color = androidx.compose.ui.graphics.Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LaunchScreenPrev(){
    LaunchScreen(onSignupClick = {},onClick = {})
}

@Preview(showBackground = true)
@Composable
private fun LaunchScreenPrevDark() = AttendanceScreensTheme(darkTheme = true){
    LaunchScreen(onSignupClick = {},onClick = {})
}