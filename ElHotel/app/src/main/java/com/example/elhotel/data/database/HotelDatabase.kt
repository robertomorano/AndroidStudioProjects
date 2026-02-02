package com.example.elhotel.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.elhotel.data.dao.HabitacionDao
import com.example.elhotel.data.dao.ReservaDao
import com.example.elhotel.data.dao.UsuarioDao
import com.example.elhotel.domain.entities.Habitacion
import com.example.elhotel.domain.entities.Reserva
import com.example.elhotel.domain.entities.User

@Database(entities = [Reserva::class, Habitacion::class, User::class], version= 1)
abstract class HotelDatabase : RoomDatabase() {
    abstract fun HabitacionDao(): HabitacionDao
    abstract fun UsuarioDao(): UsuarioDao
    abstract fun ReservaDao(): ReservaDao

}