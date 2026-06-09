package com.example.attendancescreens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancescreens.R
import com.example.attendancescreens.ui.theme.AppTextGray

@Composable
fun AttendanceHeader(modifier: Modifier = Modifier){
    val name = "Hey Hassan!"
    val subtext = "Good morning! Mark your attendance"
    val image = painterResource(R.drawable.profile)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = name,
                fontWeight = Bold,
                fontSize = 20.sp
            )
            Text(
                text = subtext,
                fontSize = 10.sp,
                color = AppTextGray,
            )
        }
        Image(
            painter = image,
            contentDescription = "profile",
            contentScale = Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
    }
}