package com.example.PPTjuego.domain.usecase

import com.example.PPTjuego.domain.model.GameChoice
import com.example.PPTjuego.domain.model.GameResult

class DetermineWinnerUseCase {
    operator fun invoke(playerChoice: GameChoice, iaChoice: GameChoice): GameResult {
        if (playerChoice == iaChoice) return GameResult.DRAW

        return when (playerChoice) {
            GameChoice.PIEDRA -> if (iaChoice == GameChoice.TIJERAS) GameResult.WIN else GameResult.LOSE
            GameChoice.PAPEL -> if (iaChoice == GameChoice.PIEDRA) GameResult.WIN else GameResult.LOSE
            GameChoice.TIJERAS -> if (iaChoice == GameChoice.PAPEL) GameResult.WIN else GameResult.LOSE
            GameChoice.RANDOM -> GameResult.DRAW
        }
    }
}