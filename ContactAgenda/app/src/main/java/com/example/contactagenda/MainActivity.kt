package com.example.contactagenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactagenda.ui.presentation.ContactForm
import com.example.contactagenda.ui.presentation.ContactsScreen
import com.example.contactagenda.ui.presentation.Setting
import com.example.contactagenda.ui.theme.ContactAgendaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val modifier = Modifier.padding(2.dp)
            var isDarkMode by remember { mutableStateOf(false) }
            ContactAgendaTheme(darkTheme = isDarkMode) {

                NavHost(
                    navController = navController,
                    startDestination = "agenda"
                ) {

                    composable("agenda") {ContactsScreen(navController, modifier)}
                    composable("add") { ContactForm(navController) }
                    composable ("settings/{isDarkMode}"){ Setting(navController) }
                }

                //ContactsScreen(modifier)
            }
        }
    }
}




