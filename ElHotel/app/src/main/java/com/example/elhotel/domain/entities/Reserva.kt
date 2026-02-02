package com.example.elhotel.domain.entities

import android.R
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reserva_entity")
data class Reserva(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var idHabitacion: Long,
    var idUsuario: Long,
    var inicio : Int,
    var final : Int,
    var cancleada: Boolean,
    var libre: Boolean
)
