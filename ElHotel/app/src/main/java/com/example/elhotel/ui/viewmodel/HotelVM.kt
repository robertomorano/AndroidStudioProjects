package com.example.elhotel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elhotel.data.repository.HotelRepository
import com.example.elhotel.domain.entities.Habitacion
import com.example.elhotel.domain.entities.Reserva
import com.example.elhotel.domain.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HotelVM(private val repository: HotelRepository) : ViewModel() {


    val listaReservas: StateFlow<List<Reserva>> = repository.getAllReservas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    private val _uiState = MutableStateFlow<String?>(null)
    val uiState: StateFlow<String?> = _uiState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    val userAdmin : User = User(name = "admin", email="admin", isAdmin = true)

    // --- FUNCIÓN DE LOGIN ---
    fun login(email: String) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            if (user != null) {
                _currentUser.value = user
                _uiState.value = "Bienvenido, ${user.name}"
            } else {
                _uiState.value = "Usuario no encontrado"
            }
        }
    }

    // --- FUNCIÓN DE REGISTRO ---
    fun registrar(nombre: String, email: String) {
        viewModelScope.launch {
            // 1. Verificar si el email ya está en uso
            val existingUser = repository.getUserByEmail(email)
            if (existingUser != null) {
                _uiState.value = "Este email ya está registrado"
                return@launch
            }

            // 2. Crear e insertar el nuevo usuario
            val nuevoUsuario = User(name = nombre, email = email)
            val id = repository.registrarUsuario(nuevoUsuario)

            if (id > 0) {
                _currentUser.value = nuevoUsuario.copy(id = id)
                _uiState.value = "Registro completado con éxito"
            } else {
                _uiState.value = "Error al registrar"
            }
        }
    }

    // --- CERRAR SESIÓN ---
    fun logout() {
        _currentUser.value = null
    }

    fun insertarReserva(reserva: Reserva) {
        viewModelScope.launch {
            try {
                repository.realizarReserva(reserva)
                _uiState.value = "Reserva creada con éxito"
            } catch (e: Exception) {
                _uiState.value = "Error al crear la reserva: ${e.message}"
            }
        }
    }

    fun cancelarReserva(reserva: Reserva) {
        viewModelScope.launch {
            try {
                repository.cancelarReserva(reserva)
                _uiState.value = "Reserva cancelada"
            } catch (e: Exception) {
                _uiState.value = "No se pudo cancelar"
            }
        }
    }

    fun eliminarReserva(reserva: Reserva) {
        viewModelScope.launch {
            repository.eliminarReserva(reserva)
        }
    }


    fun resetUiState() {
        _uiState.value = null
    }
    val listaHabitaciones: StateFlow<List<Habitacion>> = repository.getHabitaciones()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // FUNCIÓN PARA CARGA MANUAL (Si no quieres usar Flow)
    private val _habitacionesManual = MutableStateFlow<List<Habitacion>>(emptyList())
    val habitacionesManual = _habitacionesManual.asStateFlow()

    fun cargarHabitaciones() {
        viewModelScope.launch {
            // Suponiendo que aquí usas una versión 'suspend' en el repo
            // _habitacionesManual.value = repository.getAllHabitacionesSuspend()
        }
    }
}
