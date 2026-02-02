package com.example.elhotel

import DetalleHabitacion
import ListaHabitaciones
import ListaReservasUsuario
import PanelAdmin
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.elhotel.data.database.HotelDatabase
import com.example.elhotel.data.repository.HotelRepository
import com.example.elhotel.domain.entities.Habitacion
import com.example.elhotel.domain.entities.User
import com.example.elhotel.domain.factory.HotelVMFactory
import com.example.elhotel.ui.theme.ElHotelTheme
import com.example.elhotel.ui.viewmodel.HotelVM
import com.example.elhotel.ui.views.Login
import com.example.elhotel.ui.views.Registrar
import com.example.elhotel.ui.views.Reservas
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {
            val habitacionesExistentes = database.HabitacionDao().getAllHabitaciones().first()

            // Solo crear si no existen habitaciones
            if (habitacionesExistentes.isEmpty()) {
                repeat(10) { // Crear 10 habitaciones
                    database.HabitacionDao().addHabitacion(Habitacion())
                }
            }

            // OPCIONAL: Crear usuario admin si no existe
            val adminExiste = database.UsuarioDao().getUserByEmail("admin")
            if (adminExiste == null) {
                database.UsuarioDao().addUser(
                    User(name = "Admin", email = "admin", isAdmin = true)
                )
            }
        }
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ElHotelTheme {
                NavHost(
                    navController = navController,
                    startDestination = "ListaHabitaciones"
                ) {
                    // Pantalla inicial: Lista de habitaciones
                    composable("ListaHabitaciones") {
                        ListaHabitaciones(navController, HotelViewModel)
                    }

                    // Detalle de habitación para reservar
                    composable(
                        route = "DetalleHabitacion/{habitacionId}",
                        arguments = listOf(
                            navArgument("habitacionId") { type = NavType.LongType }
                        )
                    ) { backStackEntry ->
                        val habitacionId = backStackEntry.arguments?.getLong("habitacionId") ?: 0L
                        DetalleHabitacion(navController, HotelViewModel, habitacionId)
                    }

                    // Login (ya tienes este composable)
                    composable("Login") {
                        Login(navController, HotelViewModel)
                    }

                    // Registrar (ya tienes este composable)
                    composable("Registrar") {
                        Registrar(navController, HotelViewModel)
                    }

                    // Lista de reservas del usuario
                    composable("Reservas") {
                        ListaReservasUsuario(navController, HotelViewModel)
                    }

                    // Panel de administración (solo para admin)
                    composable("Admin") {
                        PanelAdmin(navController, HotelViewModel)
                    }
                }
            }
        }
    }
}

