package com.example.attendancescreens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancescreens.ui.theme.AttendanceScreensTheme
import com.example.attendancescreens.ui.theme.AppDarkGreen
import com.example.attendancescreens.ui.theme.AppGoldAccent
import com.example.attendancescreens.ui.theme.AppTextGray

@Composable
fun HistoryItem(
    modifier: Modifier = Modifier,
    date: String,
    day: String,
    checkIn: String,
    checkOut: String,
    totalHrs: String,
    location: String,
    isMainColor: Boolean = true,
) {
    val dateBoxColor = if (isMainColor) AppDarkGreen else AppGoldAccent

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(width = 60.dp, height = 70.dp),
                shape = RoundedCornerShape(12.dp),
                color = dateBoxColor
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = date,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                    Text(
                        text = day,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatColumn(time = checkIn, label = "Check In")
                    VerticalDivider(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .fillMaxHeight(),
                        thickness = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                    StatColumn(time = checkOut, label = "Check Out")
                    VerticalDivider(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .fillMaxHeight(),
                        thickness = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                    StatColumn(time = totalHrs, label = "Total Hrs")
                }

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = location,
                    fontSize = 10.sp,
                    color = AppTextGray,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun StatColumn(time: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = time,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = AppTextGray
        )
    }
}

@Preview
@Composable
private fun HistoryItemPrev() {
    AttendanceScreensTheme {
        Column {
            HistoryItem(
                date = "26",
                day = "Thu",
                checkIn = "09:00 am",
                checkOut = "--:--",
                totalHrs = "--:--",
                location = "NLS Tech Solutions Limited, Saachi Plaza",
                isMainColor = true
            )
            HistoryItem(
                date = "25",
                day = "Wed",
                checkIn = "09:00 am",
                checkOut = "18:00",
                totalHrs = "08:00",
                location = "NLS Tech Solutions Limited, Saachi Plaza",
                isMainColor = false
            )
        }
    }
}
