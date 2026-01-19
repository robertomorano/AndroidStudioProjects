package com.example.PPTjuego.domain.model

enum class GameChoice {
    PIEDRA,
    PAPEL,
    TIJERAS,
    RANDOM;

    fun getDisplayName(): String = when (this) {
        PIEDRA -> "Piedra"
        PAPEL -> "Papel"
        TIJERAS -> "Tijeras"
        RANDOM -> "Random"
    }

    fun getEmoji(): String = when (this) {
        PIEDRA -> "✊"
        PAPEL -> "✋"
        TIJERAS -> "✌️"
        RANDOM -> "🎲"
    }
}