package com.example.elhotel.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.elhotel.data.dao.HabitacionDao
import com.example.elhotel.data.dao.ReservaDao
import com.example.elhotel.data.dao.UsuarioDao
import com.example.elhotel.domain.entities.Reserva

@Database(entities = [Reserva::class], version= 1)
abstract class HotelDatabase : RoomDatabase() {
    abstract fun HabitacionDao(): HabitacionDao
    abstract fun UsuarioDao(): UsuarioDao
    abstract fun ReservaDao(): ReservaDao

}