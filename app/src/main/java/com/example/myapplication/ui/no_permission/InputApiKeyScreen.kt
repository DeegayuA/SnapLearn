package com.example.myapplication.ui.no_permission

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.SharedPrefsManager

@Composable
fun InputApiKeyScreen(
    context: Context,
    sharedPrefsManager: SharedPrefsManager,
    onApiKeyEntered: () -> Unit
) {
    var apiKey by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Please enter your API key:")
            OutlinedTextField(
                value = apiKey,
                onValueChange = { apiKey = it },
                label = { Text("Enter API Key") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                sharedPrefsManager.storeApiKey(apiKey)
                onApiKeyEntered()
            }) {
                Text(text = "Save API Key")
            }
        }
    }
}