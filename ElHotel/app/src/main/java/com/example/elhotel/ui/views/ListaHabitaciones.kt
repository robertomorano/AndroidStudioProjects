@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elhotel.domain.entities.Habitacion
import com.example.elhotel.ui.viewmodel.HotelVM

@Composable
fun ListaHabitaciones(
    navController: NavController,
    viewModel: HotelVM
) {
    val habitaciones by viewModel.listaHabitaciones.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val reservas by viewModel.listaReservas.collectAsState()

    // Filtrar habitaciones disponibles (que no tienen reserva activa)
    val habitacionesDisponibles = habitaciones.filter { habitacion ->
        reservas.none { reserva ->
            reserva.idHabitacion == habitacion.id && !reserva.cancleada && !reserva.libre
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habitaciones Disponibles") },
                actions = {
                    if (currentUser != null) {
                        // Usuario logueado
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = currentUser!!.name,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Row {
                                TextButton(onClick = { navController.navigate("Reservas") }) {
                                    Text("Reservas")
                                }
                                IconButton(onClick = { viewModel.logout() }) {
                                    Icon(Icons.Default.ExitToApp, "Logout")
                                }
                            }
                        }
                    } else {
                        // Usuario no logueado
                        Row {
                            TextButton(onClick = { navController.navigate("Login") }) {
                                Icon(Icons.Default.Person, null)
                                Spacer(Modifier.width(4.dp))
                                Text("Login")
                            }
                            TextButton(onClick = { navController.navigate("Registrar") }) {
                                Text("Registrar")
                            }
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (habitacionesDisponibles.isEmpty()) {
                // No hay habitaciones disponibles
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "NO VACANCY",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            } else {
                // Lista de habitaciones
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(habitacionesDisponibles) { habitacion ->
                        HabitacionCard(
                            habitacion = habitacion,
                            onClick = {
                                if (currentUser != null) {
                                    // Usuario logueado -> ir a detalle
                                    navController.navigate("DetalleHabitacion/${habitacion.id}")
                                } else {
                                    // No logueado -> ir a login primero
                                    navController.navigate("Login")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HabitacionCard(
    habitacion: Habitacion,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Habitación #${habitacion.id}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Disponible",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Ver detalles",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
