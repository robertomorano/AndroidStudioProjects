package com.example.PPTjuego.domain.model

data class Round(
    val roundNumber: Int,
    val playerChoice: GameChoice,
    val iaChoice: GameChoice,
    val result: GameResult
)
