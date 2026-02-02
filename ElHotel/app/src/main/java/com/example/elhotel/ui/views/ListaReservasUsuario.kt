@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elhotel.domain.entities.Habitacion
import com.example.elhotel.domain.entities.Reserva
import com.example.elhotel.ui.viewmodel.HotelVM

@Composable
fun ListaReservasUsuario(
    navController: NavController,
    viewModel: HotelVM
) {
    val reservas by viewModel.listaReservas.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val habitaciones by viewModel.listaHabitaciones.collectAsState()

    // Filtrar solo las reservas del usuario actual
    val reservasUsuario = reservas.filter { it.idUsuario == currentUser?.id }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Reservas") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
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
            if (currentUser == null) {
                // Usuario no logueado
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Debes iniciar sesión para ver tus reservas",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { navController.navigate("Login") }) {
                        Text("Iniciar Sesión")
                    }
                }
            } else if (reservasUsuario.isEmpty()) {
                // No tiene reservas
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tienes reservas",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                // Lista de reservas
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(reservasUsuario) { reserva ->
                        ReservaCard(
                            reserva = reserva,
                            habitacion = habitaciones.find { it.id == reserva.idHabitacion },
                            onCancelar = {
                                if (!reserva.libre && !reserva.cancleada) {
                                    viewModel.cancelarReserva(reserva)
                                }
                            },
                            onEliminar = {
                                // Solo eliminar si está cancelada o libre
                                if (reserva.cancleada || reserva.libre) {
                                    viewModel.eliminarReserva(reserva)
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
fun ReservaCard(
    reserva: Reserva,
    habitacion: Habitacion?,
    onCancelar: () -> Unit,
    onEliminar: () -> Unit
) {
    var showCancelDialog by remember { mutableStateOf(false) }

    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Cancelar Reserva") },
            text = { Text("¿Estás seguro de que deseas cancelar esta reserva?") },
            confirmButton = {
                TextButton(onClick = {
                    onCancelar()
                    showCancelDialog = false
                }) {
                    Text("Cancelar Reserva")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when {
                reserva.cancleada -> MaterialTheme.colorScheme.errorContainer
                reserva.libre -> MaterialTheme.colorScheme.tertiaryContainer
                else -> MaterialTheme.colorScheme.primaryContainer
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Habitación #${habitacion?.id ?: reserva.idHabitacion}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Días: ${reserva.inicio} - ${reserva.final}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = when {
                            reserva.cancleada -> "Estado: CANCELADA"
                            reserva.libre -> "Estado: FINALIZADA"
                            else -> "Estado: ACTIVA"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = when {
                            reserva.cancleada -> MaterialTheme.colorScheme.error
                            reserva.libre -> MaterialTheme.colorScheme.tertiary
                            else -> MaterialTheme.colorScheme.primary
                        }
                    )
                }

                // Botones de acción
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Botón cancelar (solo si está activa)
                    if (!reserva.cancleada && !reserva.libre) {
                        Button(
                            onClick = { showCancelDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Cancelar")
                        }
                    }

                    // Botón eliminar (solo si está cancelada o finalizada)
                    if (reserva.cancleada || reserva.libre) {
                        IconButton(onClick = onEliminar) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}
