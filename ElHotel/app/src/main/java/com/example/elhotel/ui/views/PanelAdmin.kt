@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elhotel.domain.entities.Habitacion
import com.example.elhotel.domain.entities.Reserva
import com.example.elhotel.ui.viewmodel.HotelVM
import kotlinx.coroutines.launch

@Composable
fun PanelAdmin(
    navController: NavController,
    viewModel: HotelVM
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val habitaciones by viewModel.listaHabitaciones.collectAsState()
    val reservas by viewModel.listaReservas.collectAsState()
    val scope = rememberCoroutineScope()

    var selectedTab by remember { mutableStateOf(0) }
    var showAddRoomDialog by remember { mutableStateOf(false) }

    // Verificar si es admin
    if (currentUser?.isAdmin != true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Acceso denegado",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text("Solo el administrador puede acceder a esta sección")
                Button(onClick = { navController.navigateUp() }) {
                    Text("Volver")
                }
            }
        }
        return
    }

    // Diálogo para añadir habitación
    if (showAddRoomDialog) {
        AlertDialog(
            onDismissRequest = { showAddRoomDialog = false },
            title = { Text("Añadir Habitación") },
            text = {
                Text("¿Deseas añadir una nueva habitación al hostal?")
            },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        viewModel.añadirHabitacion(Habitacion(1))
                    }
                    showAddRoomDialog = false
                }) {
                    Text("Añadir")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddRoomDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Administración") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.logout() }) {
                        Icon(Icons.Default.ExitToApp, "Cerrar sesión")
                    }
                }
            )
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(
                    onClick = { showAddRoomDialog = true }
                ) {
                    Icon(Icons.Default.Add, "Añadir habitación")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Pestañas
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Habitaciones") },
                    icon = { Icon(Icons.Default.Home, null) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Reservas") },
                    icon = { Icon(Icons.Default.DateRange, null) }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("Historial") },
                    icon = { Icon(Icons.Default.List, null) }
                )
            }

            // Contenido según pestaña
            when (selectedTab) {
                0 -> AdminHabitaciones(habitaciones, reservas, viewModel)
                1 -> AdminReservasActivas(reservas, habitaciones, viewModel)
                2 -> AdminHistorial(reservas, habitaciones)
            }
        }
    }
}

@Composable
fun AdminHabitaciones(
    habitaciones: List<Habitacion>,
    reservas: List<Reserva>,
    viewModel: HotelVM
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
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
                        text = "Total de habitaciones: ${habitaciones.size}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    val ocupadas = habitaciones.count { hab ->
                        reservas.any { it.idHabitacion == hab.id && !it.cancleada && !it.libre }
                    }
                    Text("Ocupadas: $ocupadas")
                    Text("Disponibles: ${habitaciones.size - ocupadas}")
                }
            }
        }

        items(habitaciones) { habitacion ->
            val reservaActiva = reservas.find {
                it.idHabitacion == habitacion.id && !it.cancleada && !it.libre
            }

            AdminHabitacionCard(
                habitacion = habitacion,
                reservaActiva = reservaActiva,
                onLiberar = {
                    reservaActiva?.let {
                        val reservaLiberada = it.copy(libre = true)
                        viewModel.cancelarReserva(reservaLiberada)
                    }
                }
            )
        }
    }
}

@Composable
fun AdminHabitacionCard(
    habitacion: Habitacion,
    reservaActiva: Reserva?,
    onLiberar: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Liberar Habitación") },
            text = { Text("¿Marcar esta habitación como disponible?") },
            confirmButton = {
                TextButton(onClick = {
                    onLiberar()
                    showDialog = false
                }) {
                    Text("Liberar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (reservaActiva != null)
                MaterialTheme.colorScheme.errorContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Habitación #${habitacion.id}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = if (reservaActiva != null)
                        "OCUPADA (Días ${reservaActiva.inicio}-${reservaActiva.final})"
                    else
                        "DISPONIBLE",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (reservaActiva != null)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primary
                )
            }

            if (reservaActiva != null) {
                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Liberar")
                }
            }
        }
    }
}

@Composable
fun AdminReservasActivas(
    reservas: List<Reserva>,
    habitaciones: List<Habitacion>,
    viewModel: HotelVM
) {
    val reservasActivas = reservas.filter { !it.cancleada && !it.libre }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (reservasActivas.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay reservas activas",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            }
        } else {
            items(reservasActivas) { reserva ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Habitación #${reserva.idHabitacion}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text("Usuario ID: ${reserva.idUsuario}")
                        Text("Días: ${reserva.inicio} - ${reserva.final}")
                        Text(
                            text = "Estado: ACTIVA",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminHistorial(
    reservas: List<Reserva>,
    habitaciones: List<Habitacion>
) {
    val reservasFinalizadas = reservas.filter { it.cancleada || it.libre }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Estadísticas",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Total reservas: ${reservas.size}")
                    Text("Canceladas: ${reservas.count { it.cancleada }}")
                    Text("Finalizadas: ${reservas.count { it.libre }}")
                    Text("Activas: ${reservas.count { !it.cancleada && !it.libre }}")
                }
            }
        }

        items(reservasFinalizadas) { reserva ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (reserva.cancleada)
                        MaterialTheme.colorScheme.errorContainer
                    else
                        MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Habitación #${reserva.idHabitacion}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("Usuario ID: ${reserva.idUsuario}")
                    Text("Días: ${reserva.inicio} - ${reserva.final}")
                    Text(
                        text = if (reserva.cancleada) "CANCELADA" else "FINALIZADA",
                        color = if (reserva.cancleada)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}
