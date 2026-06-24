package com.example.attendancescreens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancescreens.ui.theme.AppGoldAccent
import com.example.attendancescreens.ui.theme.AppTextGray

@Composable
fun ProfileStatItem(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AppGoldAccent,
            modifier = Modifier.size(20.dp)
        )
        Text(text = label, fontSize = 12.sp, color = AppTextGray)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}
