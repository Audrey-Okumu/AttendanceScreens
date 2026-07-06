package com.example.attendancescreens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancescreens.ui.theme.AttendanceScreensTheme
import kotlin.math.roundToInt

@Composable
fun SwipeButton(
    text: String,
    onSwipe: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector = Icons.AutoMirrored.Filled.ArrowForward
) {
    val buttonWidth = 280.dp
    val buttonHeight = 56.dp
    val thumbSize = 48.dp
    val padding = 4.dp
    
    val maxOffset = with(LocalDensity.current) { (buttonWidth - thumbSize - (padding * 2)).toPx() }
    var offsetX by remember { mutableStateOf(0f) }
    
    // Reset offset when text changes
    LaunchedEffect(text) {
        offsetX = 0f
    }

    Box(
        modifier = modifier
            .width(buttonWidth)
            .height(buttonHeight)
            .clip(CircleShape)
            .background(
                if (enabled) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f)
            )
            .padding(padding),
        contentAlignment = Alignment.CenterStart
    ) {
        // Background Text
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = if (enabled) MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        )

        // Draggable Thumb
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .size(thumbSize)
                .clip(CircleShape)
                .background(
                    if (enabled) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        if (enabled) {
                            val newValue = offsetX + delta
                            offsetX = newValue.coerceIn(0f, maxOffset)
                        }
                    },
                    onDragStopped = {
                        if (offsetX >= maxOffset * 0.9f) {
                            offsetX = maxOffset
                            onSwipe()
                        } else {
                            offsetX = 0f
                        }
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SwipeButtonPreview() {
    AttendanceScreensTheme {
        SwipeButton(
            text = "SWIPE TO CHECK IN",
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
            onSwipe = {}
        )
    }
}