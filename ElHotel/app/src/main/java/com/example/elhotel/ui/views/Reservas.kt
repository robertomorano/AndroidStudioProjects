package com.example.elhotel.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elhotel.domain.entities.Habitacion
import com.example.elhotel.ui.viewmodel.HotelVM

@Composable
fun Reservas(navController: NavController, hotelViewModel: HotelVM) {
    // Observamos los estados del ViewModel
    val listaReservas by hotelViewModel.listaReservas.collectAsState()
    val currentUser by hotelViewModel.currentUser.collectAsState()


    val listaHabitaciones by hotelViewModel.listaHabitaciones.collectAsState()

    val tiempoActual = 50 // Simulación para comparar con reserva.final

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // --- SECCIÓN 1: HABITACIONES DISPONIBLES ---
        item {
            Text("Habitaciones Disponibles", style = MaterialTheme.typography.titleLarge)
        }

        items(listaHabitaciones) { habitacion ->
            Card(
                modifier = Modifier.fillMaxWidth().clickable {
                    if (currentUser == null) {
                        // Si no está logueado, al login
                        navController.navigate("Login")
                    } else {
                        // Si está logueado, ir a reservar (ejemplo de ruta)
                        navController.navigate("DetalleHabitacion/${habitacion.id}")
                    }
                }
            ) {
                Text("Habitación número: ${habitacion.id}", modifier = Modifier.padding(16.dp))
            }
        }

        // --- SECCIÓN 2: MIS RESERVAS (Solo si está logueado) ---
        if (currentUser != null) {
            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                Text("Mis Reservas Realizadas", style = MaterialTheme.typography.titleLarge)
            }

            // Filtramos las reservas que pertenecen al usuario actual
            val misReservas = listaReservas.filter { it.idUsuario == currentUser?.id }

            items(misReservas) { reserva ->
                val esFinalizada = reserva.final < tiempoActual

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (reserva.cancleada) Color.LightGray else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Reserva ID: ${reserva.id} - Habitación: ${reserva.idHabitacion}")
                        Text("Estado: ${if (reserva.cancleada) "CANCELADA" else "ACTIVA"}")

                        // Lógica de cancelación
                        if (!reserva.cancleada && !esFinalizada) {
                            Button(
                                onClick = { hotelViewModel.cancelarReserva(reserva) },
                                modifier = Modifier.align(Alignment.End),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text("Cancelar Reserva")
                            }
                        } else if (esFinalizada) {
                            Text("Finalizada", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}