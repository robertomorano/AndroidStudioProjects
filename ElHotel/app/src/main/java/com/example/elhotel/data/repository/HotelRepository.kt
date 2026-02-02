package com.example.elhotel.data.repository

import com.example.elhotel.data.dao.HabitacionDao
import com.example.elhotel.data.dao.ReservaDao

import com.example.elhotel.data.dao.UsuarioDao
import com.example.elhotel.domain.entities.Habitacion
import com.example.elhotel.domain.entities.Reserva
import com.example.elhotel.domain.entities.User
import kotlinx.coroutines.flow.Flow


class HotelRepository(
    private val usuarioDao: UsuarioDao,
    private val habitacionDao: HabitacionDao,
    private val reservaDao: ReservaDao
) {

    // --- Operaciones de Usuario ---
    suspend fun getAllUsers(): List<User> = usuarioDao.getAllUser()

    suspend fun getUserById(id: Long): User = usuarioDao.getUserById(id)

    suspend fun insertUser(user: User) = usuarioDao.addUser(user)

    suspend fun getUserByEmail(email: String): User? = usuarioDao.getUserByEmail(email)

    suspend fun registrarUsuario(user: User): Long {

        return usuarioDao.addUser(user)
    }

    // --- Operaciones de Habitación ---
    fun getHabitaciones(): Flow<List<Habitacion>> = habitacionDao.getAllHabitaciones()


    suspend fun insertHabitacion(habitacion: Habitacion) = habitacionDao.addHabitacion(habitacion)

    // --- Operaciones de Reserva (Lógica de Negocio) ---
    fun getAllReservas(): Flow<List<Reserva>> = reservaDao.getAllReservas()

    suspend fun getReservasDeUsuario(userId: Long): List<Reserva> {
        return reservaDao.getReservasByUser(userId)
    }

    suspend fun realizarReserva(reserva: Reserva): Long {
        // Aquí podrías añadir lógica adicional antes de insertar,
        // como verificar si la habitación está libre.
        return reservaDao.addReserva(reserva)
    }

    suspend fun cancelarReserva(reserva: Reserva) {
        reserva.cancleada = true
        reservaDao.updateReserva(reserva)
    }

    suspend fun eliminarReserva(reserva: Reserva) = reservaDao.deleteReserva(reserva)


}