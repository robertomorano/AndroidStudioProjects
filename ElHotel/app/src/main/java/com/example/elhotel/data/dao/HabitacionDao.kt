package com.example.elhotel.data.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.elhotel.domain.entities.Habitacion
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitacionDao {
    @Query("SELECT * FROM habitacion_entity")
    fun getAllHabitaciones(): Flow<List<Habitacion>>

    @Query("SELECT * FROM habitacion_entity WHERE id = :id")
    suspend fun getHabitacionById(id: Long): Habitacion

    @Insert
    suspend fun addHabitacion(habitacion: Habitacion): Long

    @Update
    suspend fun updateHabitacion(habitacion: Habitacion): Int

    @Delete
    suspend fun deleteHabitacion(habitacion: Habitacion): Int

    @Query("SELECT COUNT(*) FROM habitacion_entity")
    suspend fun getTotalHabitaciones(): Int
}