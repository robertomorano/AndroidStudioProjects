package com.example.elhotel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.elhotel.data.database.HotelDatabase
import com.example.elhotel.data.repository.HotelRepository
import com.example.elhotel.domain.factory.HotelVMFactory
import com.example.elhotel.ui.theme.ElHotelTheme
import com.example.elhotel.ui.viewmodel.HotelVM
import com.example.elhotel.ui.views.Login
import com.example.elhotel.ui.views.Registrar
import com.example.elhotel.ui.views.Reservas
import kotlin.getValue

class MainActivity : ComponentActivity() {


    companion object{
        lateinit var database: HotelDatabase
    }

    private val hotelRepository by lazy { HotelRepository(database.UsuarioDao(),
        database.HabitacionDao(), database.ReservaDao()) }
    private val HotelViewModel: HotelVM by viewModels(
        factoryProducer = { HotelVMFactory(hotelRepository) }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            context =  this,
            klass =  HotelDatabase::class.java,
            name = "hotel-db"
        ).build()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ElHotelTheme {
                NavHost(
                    navController = navController,
                    startDestination = "Reservas"
                ) {
                    composable("Reservas") {
                        Reservas(navController,  HotelViewModel)
                    }
                    composable("Login") {
                        Login(navController, HotelViewModel)
                    }
                    composable("Registrar"){
                        Registrar(navController, HotelViewModel)
                    }
                }
            }
        }
    }
}

