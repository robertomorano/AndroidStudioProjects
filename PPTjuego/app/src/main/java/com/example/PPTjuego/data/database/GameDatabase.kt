package com.example.PPTjuego.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.PPTjuego.domain.model.GameState

@Database(entities = [GameState::class], version= 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameState
}