package com.example.PPTjuego.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Game_Entity")
data class GameState(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val playerName: String = "",
    val totalRounds: Int = 3,
    val currentRound: Int = 0,
    val playerScore: Int = 0,
    val iaScore: Int = 0,
    val rounds: List<Round> = emptyList(),
    val isGameFinished: Boolean = false,
    val lastRoundResult: GameResult? = null
)
