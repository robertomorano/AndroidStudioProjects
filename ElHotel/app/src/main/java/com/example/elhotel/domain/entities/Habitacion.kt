package com.example.elhotel.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habitacion_entity")
data class Habitacion(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
)
