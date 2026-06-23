package com.example.attendancescreens.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.attendancescreens.R
import com.example.attendancescreens.components.SocialIcon
import com.example.attendancescreens.components.TextField
import com.example.attendancescreens.ui.theme.AppDarkGreen
import com.example.attendancescreens.ui.theme.AppGoldAccent
import com.example.attendancescreens.ui.theme.AppTextGray
import com.example.attendancescreens.model.SignUpStateType
import com.example.attendancescreens.ui.viewmodel.SignUpViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSignupClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: SignUpViewModel = viewModel()
) {
    val loginState by viewModel.signupState.collectAsState()

    val context = androidx.compose.ui.platform.LocalContext.current
    LaunchedEffect(loginState.stateType, loginState.errorMessage) {
        if (loginState.stateType == SignUpStateType.SIGNED_IN) {
            onLoginSuccess()
        } else if (loginState.stateType == SignUpStateType.ERROR) {
            android.widget.Toast.makeText(context, loginState.errorMessage ?: "Login failed", android.widget.Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 26.dp, bottom = 20.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to HR Connect",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = AppGoldAccent,
            modifier = Modifier.padding(top = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Login Illustration",
            modifier = Modifier
                .size(200.dp)
                .padding(vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Employee Email",
                fontWeight = FontWeight.SemiBold,
                color = AppTextGray,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Employee Email",
                leadingIcon = Icons.Default.Email
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Password",
                fontWeight = FontWeight.SemiBold,
                color = AppTextGray,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                passwordVisible = passwordVisible,
                onPasswordToggle = { passwordVisible = !passwordVisible }
            )

            TextButton(
                onClick = {},
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Forgot Password?",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = { viewModel.signInWithEmail(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppDarkGreen),
            shape = RoundedCornerShape(28.dp),
            enabled = email.isNotBlank() && password.isNotBlank()
        ) {
            Text(
                text = "Log In",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AppGoldAccent
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(
                Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
            )
            Text(
                " or continue with ",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            HorizontalDivider(
                Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
            )
        }

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            SocialIcon(
                image = painterResource(R.drawable.google),
                onClick = {viewModel.signUp()}
            )
        }

        Spacer(Modifier.height(8.dp))


        Row(Modifier.padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Do not have an account? ",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Text(
                "Sign Up",
                color = AppGoldAccent,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(onClick = onSignupClick)
            )
        }


        Spacer(Modifier.height(12.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onSignupClick = {}, onLoginSuccess = {})
}
