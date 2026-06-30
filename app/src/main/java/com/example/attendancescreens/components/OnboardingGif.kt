package com.example.attendancescreens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.attendancescreens.R

@Composable
fun OnboardingGif() {
    AsyncImage(
        model = R.drawable.onboarding,
        contentDescription = "Onboarding GIF",
        modifier = Modifier
            .size(220.dp)
    )
}