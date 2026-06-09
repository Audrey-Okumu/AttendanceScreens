package com.example.attendancescreens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.getDisplayedMonth
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.setDisplayedMonth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancescreens.ui.theme.AttendanceScreensTheme
import java.time.LocalDate
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDate.now().atStartOfDay().toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
    )
){


    val nextMonth = {
        datePickerState.setDisplayedMonth(datePickerState.getDisplayedMonth().plusMonths(1))
    }

    val prevMonth = {
        datePickerState.setDisplayedMonth(datePickerState.getDisplayedMonth().minusMonths(1))
    }




    val locale = LocalLocale.current.platformLocale
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextButton(
                onClick = { prevMonth()},
            ) {
                Text(
                    text = " << Prev",
                    fontSize = 16.sp
                )
            }

            val displayMonth = datePickerState.getDisplayedMonth()

             val display = with(displayMonth){
                 "${month.name.capitaliseName(locale)} $year"
             }

            Text(
                text = display,
                fontSize = 20.sp
            )
            TextButton(
                onClick = {
                    nextMonth.invoke()
                },
            ) {
                Text(
                    text = "next >>",
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(60.dp))
        Box {
            DatePicker(
                state = datePickerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-56).dp),
                showModeToggle = false,
                title = null,
                headline = null,
                colors = DatePickerDefaults.colors(
                    navigationContentColor = Color.Transparent,
                    headlineContentColor = Color.Transparent
                ),
            )
        }
    }
}

@Composable
private fun String.capitaliseName(locale: Locale): String = this.lowercase(locale)
    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }


@Preview(showBackground = true)
@Composable
private fun CalendarPrev(){
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDate.now().atStartOfDay().toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
    )

    AttendanceScreensTheme {
        Calendar(datePickerState = datePickerState)
    }
}
