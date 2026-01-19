package com.example.PPTjuego

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.PPTjuego.data.database.GameDatabase
import com.example.PPTjuego.presentation.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    companion object{
        lateinit var database: GameDatabase
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.example.PPTjuego.MainActivity.Companion.database = Room.databaseBuilder(
            this,
            GameDatabase::class.java,
            "game-db"
        ).build()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
        
    }

}