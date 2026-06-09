package com.example.attendancescreens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.getSelectedDate
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.attendancescreens.components.AttendanceBottomNav
import com.example.attendancescreens.components.Calendar
import com.example.attendancescreens.components.CalendarRoute
import com.example.attendancescreens.components.HistoryItem
import com.example.attendancescreens.components.HomeRoute
import com.example.attendancescreens.model.AttendanceHistoryData
import com.example.attendancescreens.model.AttendanceNavigationItem
import com.example.attendancescreens.ui.theme.AppDarkGreen
import com.example.attendancescreens.ui.theme.AppLightGray
import com.example.attendancescreens.ui.theme.AppTextGray
import com.example.attendancescreens.ui.theme.AttendanceScreensTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AttendanceScreensTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    val items: List<AttendanceNavigationItem> = listOf(
        AttendanceNavigationItem(id = 1, name = "Home", icon = Icons.Default.Home),
        AttendanceNavigationItem(id = 2, name = "Calendar", icon = Icons.Filled.CalendarMonth),
        AttendanceNavigationItem(id = 3, name = "Profile", icon = Icons.Filled.AccountCircle)
    )

    val selectedState = remember { mutableStateOf(items[0]) }
    var selected by selectedState
    Scaffold(
        modifier= modifier.fillMaxSize(),
        topBar = {
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
        },
        bottomBar = {
            AttendanceBottomNav(
                navItems = items,
                selected = selected,
                onItemSelected = { navItem ->
                    selected = navItem
                    when (navItem.id) {
                        1 -> navController.navigate(HomeRoute)
                        2 -> navController.navigate(CalendarRoute)
                    }
                }
            )
        }
        ){
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> {
                DashboardScreen(onClick = { })
            }
            composable<CalendarRoute> {
                CalendarScreen(

                )
            }
        }
    }
}

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val locale = LocalConfiguration.current.locales[0]

    var isCheckedIn by remember { mutableStateOf(false) }
    var checkInTime by remember { mutableStateOf("--:--") }
    var checkOutTime by remember { mutableStateOf("--:--") }

    var checkInMillis by remember {mutableStateOf<Long?>(null)}
    var checkOutMillis by remember {mutableStateOf<Long?>(null)}

    val totalHours =
        if (checkInMillis != null && checkOutMillis != null) {

            val diffMillis = checkOutMillis!! - checkInMillis!!

            val hours = diffMillis / (1000 * 60 * 60)
            val minutes = (diffMillis / (1000 * 60)) % 60

            "${hours}h ${minutes}m"
        } else {
            "--:--"
        }
    val currentDate = SimpleDateFormat("MMMM dd, yyyy - EEEE", locale).format(Date())
    
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = SimpleDateFormat("HH:mm",locale).format(Date()),
                    fontSize = 60.sp
                )
                Text(
                    text = currentDate,
                    color = AppTextGray
                )
                Spacer(modifier = Modifier.height(60.dp))
                Surface(
                    shape = CircleShape,
                    color = White,
                    shadowElevation = 10.dp,
                    modifier = Modifier
                        .size(180.dp)
                        .clickable {
                            val now = System.currentTimeMillis()
                            val currentTime = SimpleDateFormat("HH:mm",locale).format(Date(now))
                            if (!isCheckedIn){
                                checkInTime = currentTime
                                checkInMillis = now
                                isCheckedIn = true
                            } else {
                                checkOutTime = currentTime
                                checkOutMillis = now
                                isCheckedIn = false
                            }
                            onClick()
                        },
                    border = BorderStroke(20.dp, AppLightGray)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.TouchApp,
                            contentDescription = null,
                            tint = AppDarkGreen,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isCheckedIn) "Check out" else "Check in",
                            color = AppDarkGreen,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(60.dp))
            }
            Spacer(modifier = Modifier.height(60.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatusItem(
                    icon = Icons.AutoMirrored.Filled.Login,
                    time = checkInTime,
                    label = "Check In"
                )
                StatusItem(
                    icon = Icons.AutoMirrored.Filled.Logout,
                    time = checkOutTime,
                    label = "Check Out")
                StatusItem(
                    icon = Icons.Default.History,
                    time = totalHours,
                    label = "Total Hrs"
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(24.dp))

            }
        }
    }
}

@Composable
fun StatusItem(icon: ImageVector, time: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AppDarkGreen,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = time,
            fontWeight = Bold,
            fontSize = 16.sp
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = AppTextGray
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier
) {
    val historyItems: List<AttendanceHistoryData> = listOf(
        AttendanceHistoryData("26", "Thu", "09:00 am", "--:--", "--:--", true),
        AttendanceHistoryData("25", "Wed", "09:00 am", "18:00", "08:00", false),
        AttendanceHistoryData("24", "Tue", "09:00 am", "18:00", "08:00", false)
    )
    val locale = LocalLocale.current.platformLocale

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Attendance History",
                    fontWeight = Bold,
                    fontSize = 24.sp,
                )
            }

            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = LocalDate.now().atStartOfDay().toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
            )

            val currentDate = datePickerState.getSelectedDate()

            Calendar(datePickerState = datePickerState)
            Spacer(modifier = Modifier.height(16.dp))

            val date: String = currentDate?.dayOfMonth?.toString() ?: "--"
            val day: String = datePickerState.selectedDateMillis?.let {
                SimpleDateFormat("EEE", locale).format(Date(it))
            } ?: "--"

            historyItems.forEach { item: AttendanceHistoryData ->
                HistoryItem(
                    date = date,
                    day = day,
                    checkIn = item.checkIn,
                    checkOut = item.checkOut,
                    totalHrs = item.totalHrs,
                    location = "NLS Tech Solutions Limited, Saachi Plaza",
                    isMainColor = item.isMainColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AttendanceScreensTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreviewDark() {
    AttendanceScreensTheme(darkTheme = true) {
        MainScreen()
    }
}
