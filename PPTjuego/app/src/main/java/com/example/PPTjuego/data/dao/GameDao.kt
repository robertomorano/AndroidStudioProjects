package com.example.PPTjuego.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.PPTjuego.domain.model.GameState

@Dao
interface GameDao {
    @Query("SELECT * FROM game_entity")
    suspend fun getAllTask(): List<GameState>

    @Query("SELECT * FROM game_entity where id = :id")
    suspend fun getTaskById(id: Long): GameState

    @Insert
    suspend fun addTask(taskEntity: GameState):Long

    @Update
    suspend fun  updateTask(task : GameState):Int

    @Delete
    suspend fun deleteTask(task : GameState):Int
}