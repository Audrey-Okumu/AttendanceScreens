package com.example.attendancescreens.components

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.collections.isNotEmpty

const val TAG = "TEST MAP"

@Suppress("unused")
@Composable
fun Map() {
    val context = LocalContext.current

    //Check if permission is granted
    var locationPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        )
    }

    //request user permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        val fineGranted =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false

        val coarseGranted =
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        locationPermissionGranted = fineGranted || coarseGranted
    }

    LaunchedEffect(Unit) {
        if (!locationPermissionGranted) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    //  Location state
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    var isLoadingLocation by remember { mutableStateOf(false) }
    var addressText by remember { mutableStateOf("Fetching your location...") }

    //Provider client
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    LaunchedEffect(locationPermissionGranted) {
        if (!locationPermissionGranted) return@LaunchedEffect

        isLoadingLocation = true

        //Get Current location
        try {
            val location = fusedLocationClient
                .getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    CancellationTokenSource().token
                )
                .await()

            //get location latitude and longitude
            if (location != null) {
                val lat = location.latitude
                val lng = location.longitude

                Log.d("LOCATION", "MapScreen: My lat: $lat and log $lng")
                currentLocation = LatLng(lat, lng)
                isLoadingLocation = false

                //Reverse geocoding
                val geocoder = Geocoder(context, Locale.getDefault())

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(
                        lat,
                        lng,
                        1,
                        object : Geocoder.GeocodeListener {
                            override fun onGeocode(addresses: MutableList<Address>) {
                                if (addresses.isNotEmpty()) {
                                    addressText = addresses[0].getAddressLine(0)
                                }
                            }

                            override fun onError(errorMessage: String?) {
                                addressText = "Address not found"
                            }
                        }
                    )
                } else {
                    withContext(Dispatchers.IO) {
                        try {
                            @Suppress("DEPRECATION")
                            val addresses = geocoder.getFromLocation(lat, lng, 1)
                            if (!addresses.isNullOrEmpty()) {
                                addressText = addresses[0].getAddressLine(0)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Error in reverse geocoding", e)
                            addressText = "Address not found"
                        }
                    }
                }
            } else {
                addressText = "Could not fetch location"
                isLoadingLocation = false
            }
        } catch (securityError: SecurityException) {
            isLoadingLocation = false
            addressText = "Location permission was revoked"
            Log.e(TAG, "MapScreen: Location permission revoked", securityError)
        }
    }

    // Display the results
    androidx.compose.foundation.layout.Column {
        if (isLoadingLocation) {
            androidx.compose.material3.CircularProgressIndicator()
        }
        androidx.compose.material3.Text(text = addressText)
        currentLocation?.let {
            androidx.compose.material3.Text(text = "Lat: ${it.latitude}, Lng: ${it.longitude}")
        }
    }
}