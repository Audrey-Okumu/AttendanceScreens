package com.example.attendancescreens.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.attendancescreens.R
import com.example.attendancescreens.components.SocialIcon
import com.example.attendancescreens.components.TextField
import com.example.attendancescreens.ui.theme.AppDarkGreen
import com.example.attendancescreens.ui.theme.AppGoldAccent
import com.example.attendancescreens.ui.theme.AppMediumGreen
import com.example.attendancescreens.ui.theme.AppTextGray
import com.example.attendancescreens.model.SignUpStateType
import com.example.attendancescreens.ui.viewmodel.SignUpViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSignupClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: SignUpViewModel = viewModel(factory = SignUpViewModel.Factory)
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

    LoginScreenContent(
        modifier = modifier,
        onSignupClick = onSignupClick,
        onSignInClick = { email, password -> viewModel.signInWithEmail(email, password) },
        onSocialSignInClick = { viewModel.signUp() }
    )
}

@Composable
fun LoginScreenContent(
    onSignupClick: () -> Unit,
    onSignInClick: (String, String) -> Unit,
    onSocialSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                AppMediumGreen
                            )
                        )
                    )
            ) {
                // Gold accent line at the bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                        .align(Alignment.BottomCenter)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.login),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "ATTENDANCE PRO",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Welcome Back",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Email Address",
                        leadingIcon = Icons.Default.Email
                    )
                    
                    Column {
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
                            modifier = Modifier.align(Alignment.End),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Forgot Password?",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }

                Button(
                    onClick = { onSignInClick(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(14.dp),
                    enabled = email.isNotBlank() && password.isNotBlank(),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = "SIGN IN",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    HorizontalDivider(
                        Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                    Text(
                        " OR ",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    HorizontalDivider(
                        Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                }

                OutlinedButton(
                    onClick = onSocialSignInClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.google),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Google",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Don't have an account? ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                    Text(
                        "SIGN UP",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        ),
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.clickable(onClick = onSignupClick)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        onSignupClick = {},
        onSignInClick = { _, _ -> },
        onSocialSignInClick = {}
    )
}
