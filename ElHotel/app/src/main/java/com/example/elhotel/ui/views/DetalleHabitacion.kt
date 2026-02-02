@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elhotel.domain.entities.Reserva
import com.example.elhotel.ui.viewmodel.HotelVM

@Composable
fun DetalleHabitacion(
    navController: NavController,
    viewModel: HotelVM,
    habitacionId: Long
) {
    val habitaciones by viewModel.listaHabitaciones.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val habitacion = habitaciones.find { it.id == habitacionId }

    var diaInicio by remember { mutableStateOf("") }
    var diaFinal by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    // Mostrar diálogo de confirmación
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Reserva Confirmada") },
            text = { Text("Tu reserva se ha realizado con éxito") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate("Reservas") {
                        popUpTo("ListaHabitaciones") { inclusive = false }
                    }
                }) {
                    Text("Ver mis reservas")
                }
            }
        )
    }

    // Mostrar errores
    LaunchedEffect(uiState) {
        if (uiState != null && uiState!!.contains("Error")) {
            // Aquí podrías mostrar un Snackbar o Toast
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reservar Habitación") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (habitacion == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Habitación no encontrada")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Información de la habitación
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Habitación #${habitacion.id}",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Estado: Disponible",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Formulario de reserva
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Datos de la reserva",
                            style = MaterialTheme.typography.titleLarge
                        )

                        OutlinedTextField(
                            value = diaInicio,
                            onValueChange = { diaInicio = it },
                            label = { Text("Día de inicio (número)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = diaFinal,
                            onValueChange = { diaFinal = it },
                            label = { Text("Día final (número)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Text(
                            text = "Usuario: ${currentUser?.name ?: "Desconocido"}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                // Botón de reservar
                Button(
                    onClick = {
                        val inicio = diaInicio.toIntOrNull()
                        val final = diaFinal.toIntOrNull()

                        if (inicio != null && final != null && currentUser != null) {
                            val nuevaReserva = Reserva(
                                idHabitacion = habitacion.id,
                                idUsuario = currentUser!!.id,
                                inicio = inicio,
                                final = final,
                                cancleada = false,
                                libre = false
                            )
                            viewModel.insertarReserva(nuevaReserva)
                            showDialog = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = diaInicio.isNotBlank() && diaFinal.isNotBlank()
                ) {
                    Text("Confirmar Reserva")
                }
            }
        }
    }
}
