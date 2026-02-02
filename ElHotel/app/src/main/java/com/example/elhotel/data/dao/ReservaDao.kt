package com.example.elhotel.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.elhotel.domain.entities.Reserva
import kotlinx.coroutines.flow.Flow

@Dao
    interface ReservaDao {
        @Query("SELECT * FROM reserva_entity")
        fun getAllReservas(): Flow<List<Reserva>> // Sin 'suspend', Room maneja el Flow automáticamente

        @Query("SELECT * FROM reserva_entity WHERE id = :id")
        suspend fun getReservaById(id: Long): Reserva

        // Obtener todas las reservas de un usuario específico
        @Query("SELECT * FROM reserva_entity WHERE idUsuario = :idUsuario")
        suspend fun getReservasByUser(idUsuario: Long): List<Reserva>

        // Obtener reservas de una habitación específica
        @Query("SELECT * FROM reserva_entity WHERE idHabitacion = :idHabitacion")
        suspend fun getReservasByHabitacion(idHabitacion: Long): List<Reserva>

        // Obtener reservas activas (que no estén canceladas y no estén libres)
        @Query("SELECT * FROM reserva_entity WHERE cancleada = 0 AND libre = 0")
        suspend fun getActiveReservas(): List<Reserva>

        @Insert
        suspend fun addReserva(reserva: Reserva): Long

        @Update
        suspend fun updateReserva(reserva: Reserva): Int

        @Delete
        suspend fun deleteReserva(reserva: Reserva): Int

    }
