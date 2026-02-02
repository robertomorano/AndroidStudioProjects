package com.example.elhotel.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var email : String,
    var name : String = "",
    var isAdmin: Boolean = false
)
