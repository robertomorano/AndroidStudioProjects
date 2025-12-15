package com.example.contactagenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.contactagenda.data.database.ContactDatabase
import com.example.contactagenda.ui.presentation.ContactForm
import com.example.contactagenda.ui.presentation.ContactsScreen
import com.example.contactagenda.ui.presentation.Setting
import com.example.contactagenda.ui.theme.ContactAgendaTheme

class MainActivity : ComponentActivity() {
    companion object{
        lateinit var database: ContactDatabase
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            this,
            ContactDatabase::class.java,
            "contact-db"
        ).build()
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




