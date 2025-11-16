package com.example.traveljournal.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.preference.PreferenceFragmentCompat
import com.example.traveljournal.R

@Composable
fun Setting(navController: NavController){
    var isDarkMode by remember { mutableStateOf(false) }
    DarkModeSwitch(
        isDarkMode,
        onToggle = {isDarkMode = !isDarkMode}
    )
}






@Composable
fun DarkModeSwitch(
    isDarkMode: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Modo noche")
        Spacer(Modifier.width(8.dp))
        Switch(
            checked = isDarkMode,
            onCheckedChange = { onToggle(it) }
        )
    }
}
