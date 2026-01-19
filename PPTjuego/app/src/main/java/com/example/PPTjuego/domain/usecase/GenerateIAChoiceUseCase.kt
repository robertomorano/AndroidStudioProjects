package com.example.PPTjuego.domain.usecase

import com.example.PPTjuego.domain.model.GameChoice
import kotlin.random.Random

class GenerateIAChoiceUseCase {
    operator fun invoke(): GameChoice {
        val choices = listOf(GameChoice.PIEDRA, GameChoice.PAPEL, GameChoice.TIJERAS)
        return choices[Random.nextInt(choices.size)]
    }
}