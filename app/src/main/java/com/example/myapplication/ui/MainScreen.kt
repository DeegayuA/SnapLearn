@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.myapplication.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.myapplication.CameraScreen
import com.example.myapplication.NoPermissionScreen
import com.example.myapplication.SharedPrefsManager
import com.example.myapplication.ui.no_permission.InputApiKeyScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun MainScreen(sharedPrefsManager: SharedPrefsManager, navController: NavController) { // Accept NavController parameter
    val context = LocalContext.current // Get the context
    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val apiKey by remember { mutableStateOf(sharedPrefsManager.getApiKey()) } // Check for stored API key

    if (apiKey == null || apiKey == "apikey") {
        InputApiKeyScreen(context = context, sharedPrefsManager = sharedPrefsManager) {
            navController.navigate("camera_screen_route")
        }
    } else {
        MainContent(
            context = context,
            sharedPrefsManager = sharedPrefsManager,
            hasPermission = cameraPermissionState.status.isGranted,
            onRequestPermission = cameraPermissionState::launchPermissionRequest
        )
    }
}

@Composable
private fun MainContent(
    context: Context,
    sharedPrefsManager: SharedPrefsManager,
    hasPermission: Boolean,
    onRequestPermission: () -> Unit
) {
    if (hasPermission) {
        CameraScreen(context = context, sharedPrefsManager = sharedPrefsManager)
    } else {
        NoPermissionScreen(onRequestPermission)
    }
}

@Preview
@Composable
private fun Preview_MainContent() {
    MainContent(
        context = LocalContext.current,
        sharedPrefsManager = SharedPrefsManager(context = LocalContext.current),
        hasPermission = true,
        onRequestPermission = {}
    )
}
