package com.example.elhotel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elhotel.data.repository.HotelRepository
import com.example.elhotel.domain.entities.Habitacion
import com.example.elhotel.domain.entities.Reserva
import com.example.elhotel.domain.entities.User
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HotelVM(private val repository: HotelRepository) : ViewModel() {

    // --- ESTADOS OBSERVABLES ---

    // Lista de todas las reservas
    val listaReservas: StateFlow<List<Reserva>> = repository.getAllReservas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Lista de todas las habitaciones
    val listaHabitaciones: StateFlow<List<Habitacion>> = repository.getHabitaciones()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Estado de la UI (mensajes, errores, etc.)
    private val _uiState = MutableStateFlow<String?>(null)
    val uiState: StateFlow<String?> = _uiState.asStateFlow()

    // Usuario actual logueado
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // Usuario admin por defecto (puedes crearlo en la BD si quieres)
    val userAdmin: User = User(name = "admin", email = "admin", isAdmin = true)


    // --- FUNCIONES DE AUTENTICACIÓN ---

    /**
     * Inicia sesión con un email
     */
    fun login(email: String) {
        viewModelScope.launch {
            try {
                val user = repository.getUserByEmail(email)
                if (user != null) {
                    _currentUser.value = user
                    _uiState.value = "Bienvenido, ${user.name}"
                } else {
                    _uiState.value = "Usuario no encontrado"
                }
            } catch (e: Exception) {
                _uiState.value = "Error al iniciar sesión: ${e.message}"
            }
        }
    }

    /**
     * Registra un nuevo usuario
     */
    fun registrar(nombre: String, email: String) {
        viewModelScope.launch {
            try {
                // Verificar si el email ya está en uso
                val existingUser = repository.getUserByEmail(email)
                if (existingUser != null) {
                    _uiState.value = "Este email ya está registrado"
                    return@launch
                }

                // Crear e insertar el nuevo usuario
                val nuevoUsuario = User(name = nombre, email = email)
                val id = repository.registrarUsuario(nuevoUsuario)

                if (id > 0) {
                    _currentUser.value = nuevoUsuario.copy(id = id)
                    _uiState.value = "Registro completado con éxito"
                } else {
                    _uiState.value = "Error al registrar"
                }
            } catch (e: Exception) {
                _uiState.value = "Error en el registro: ${e.message}"
            }
        }
    }

    /**
     * Cierra la sesión del usuario actual
     */
    fun logout() {
        _currentUser.value = null
        _uiState.value = null
    }


    // --- FUNCIONES DE RESERVAS ---

    /**
     * Crea una nueva reserva
     */
    fun insertarReserva(reserva: Reserva) {
        viewModelScope.launch {
            try {
                // Verificar que la habitación esté disponible
                val reservasActivas = listaReservas.value.filter {
                    it.idHabitacion == reserva.idHabitacion &&
                            !it.cancleada &&
                            !it.libre
                }

                if (reservasActivas.isNotEmpty()) {
                    _uiState.value = "Esta habitación ya está reservada"
                    return@launch
                }

                repository.realizarReserva(reserva)
                _uiState.value = "Reserva creada con éxito"
            } catch (e: Exception) {
                _uiState.value = "Error al crear la reserva: ${e.message}"
            }
        }
    }

    /**
     * Cancela una reserva (solo si está activa)
     */
    fun cancelarReserva(reserva: Reserva) {
        viewModelScope.launch {
            try {
                // Solo permitir cancelar si no está ya cancelada o finalizada
                if (reserva.cancleada || reserva.libre) {
                    _uiState.value = "No se puede cancelar esta reserva"
                    return@launch
                }

                repository.cancelarReserva(reserva)
                _uiState.value = "Reserva cancelada"
            } catch (e: Exception) {
                _uiState.value = "No se pudo cancelar: ${e.message}"
            }
        }
    }

    /**
     * Elimina una reserva de la base de datos
     */
    fun eliminarReserva(reserva: Reserva) {
        viewModelScope.launch {
            try {
                repository.eliminarReserva(reserva)
                _uiState.value = "Reserva eliminada"
            } catch (e: Exception) {
                _uiState.value = "Error al eliminar: ${e.message}"
            }
        }
    }

    /**
     * Obtiene las reservas de un usuario específico
     */
    fun getReservasByUsuario(userId: Long): StateFlow<List<Reserva>> {
        return listaReservas.map { reservas ->
            reservas.filter { it.idUsuario == userId }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }


    // --- FUNCIONES DE HABITACIONES ---

    /**
     * Añade una nueva habitación (solo admin)
     */
    fun añadirHabitacion(habitacion: Habitacion) {
        viewModelScope.launch {
            try {
                repository.insertHabitacion(habitacion)
                _uiState.value = "Habitación añadida correctamente"
            } catch (e: Exception) {
                _uiState.value = "Error al añadir habitación: ${e.message}"
            }
        }
    }

    /**
     * Obtiene habitaciones disponibles (sin reservas activas)
     */
    fun getHabitacionesDisponibles(): StateFlow<List<Habitacion>> {
        return combine(listaHabitaciones, listaReservas) { habitaciones, reservas ->
            habitaciones.filter { habitacion ->
                reservas.none { reserva ->
                    reserva.idHabitacion == habitacion.id &&
                            !reserva.cancleada &&
                            !reserva.libre
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    /**
     * Obtiene habitaciones ocupadas (con reservas activas)
     */
    fun getHabitacionesOcupadas(): StateFlow<List<Habitacion>> {
        return combine(listaHabitaciones, listaReservas) { habitaciones, reservas ->
            habitaciones.filter { habitacion ->
                reservas.any { reserva ->
                    reserva.idHabitacion == habitacion.id &&
                            !reserva.cancleada &&
                            !reserva.libre
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }


    // --- FUNCIONES DE ADMINISTRACIÓN ---

    /**
     * Libera una habitación marcando su reserva como libre (solo admin)
     */
    fun liberarHabitacion(reserva: Reserva) {
        viewModelScope.launch {
            try {
                if (currentUser.value?.isAdmin != true) {
                    _uiState.value = "Solo el administrador puede liberar habitaciones"
                    return@launch
                }

                val reservaLiberada = reserva.copy(libre = true)
                repository.cancelarReserva(reservaLiberada)
                _uiState.value = "Habitación liberada"
            } catch (e: Exception) {
                _uiState.value = "Error al liberar: ${e.message}"
            }
        }
    }

    /**
     * Obtiene estadísticas generales
     */
    fun getEstadisticas(): StateFlow<Estadisticas> {
        return combine(listaHabitaciones, listaReservas) { habitaciones, reservas ->
            Estadisticas(
                totalHabitaciones = habitaciones.size,
                habitacionesOcupadas = habitaciones.count { hab ->
                    reservas.any { it.idHabitacion == hab.id && !it.cancleada && !it.libre }
                },
                totalReservas = reservas.size,
                reservasActivas = reservas.count { !it.cancleada && !it.libre },
                reservasCanceladas = reservas.count { it.cancleada },
                reservasFinalizadas = reservas.count { it.libre }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Estadisticas()
        )
    }


    // --- FUNCIONES AUXILIARES ---

    /**
     * Resetea el estado de la UI
     */
    fun resetUiState() {
        _uiState.value = null
    }

    /**
     * Verifica si un usuario es administrador
     */
    fun isAdmin(): Boolean {
        return currentUser.value?.isAdmin == true
    }

    /**
     * Obtiene una habitación por ID
     */
    fun getHabitacionById(id: Long): Habitacion? {
        return listaHabitaciones.value.find { it.id == id }
    }

    /**
     * Obtiene una reserva por ID
     */
    fun getReservaById(id: Long): Reserva? {
        return listaReservas.value.find { it.id == id }
    }
}


// --- DATA CLASS PARA ESTADÍSTICAS ---
data class Estadisticas(
    val totalHabitaciones: Int = 0,
    val habitacionesOcupadas: Int = 0,
    val totalReservas: Int = 0,
    val reservasActivas: Int = 0,
    val reservasCanceladas: Int = 0,
    val reservasFinalizadas: Int = 0
) {
    val habitacionesDisponibles: Int
        get() = totalHabitaciones - habitacionesOcupadas
}