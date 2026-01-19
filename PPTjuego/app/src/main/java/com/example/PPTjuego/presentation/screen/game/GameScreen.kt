package com.example.PPTjuego.presentation.screen.game

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.PPTjuego.domain.model.GameChoice
import com.example.PPTjuego.domain.model.GameResult
import kotlinx.coroutines.delay

    @Composable
    fun GameScreen(
        playerName: String,
        totalRounds: Int,
        onGameFinished: (String) -> Unit,
        viewModel: GameViewModel = viewModel { GameViewModel(playerName, totalRounds) }
    ) {
        val gameState by viewModel.gameState.collectAsState()
        var showResultDialog by remember { mutableStateOf(false) }

        LaunchedEffect(gameState.lastRoundResult) {
            if (gameState.lastRoundResult != null) {
                showResultDialog = true
                delay(2000)
                showResultDialog = false
                viewModel.resetLastResult()
            }
        }

        LaunchedEffect(gameState.isGameFinished) {
            if (gameState.isGameFinished) {
                delay(2500)
                val winner = if (gameState.playerScore > gameState.iaScore) {
                    playerName
                } else if (gameState.iaScore > gameState.playerScore) {
                    "IA: ROBO-CRACK"
                } else {
                    "Empate"
                }
                onGameFinished(winner)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1E88E5), Color(0xFF1565C0))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ScoreDisplay("Jugador: $playerName", gameState.playerScore, Color(0xFF4CAF50))
                        ScoreDisplay("IA: ROBO-CRACK", gameState.iaScore, Color(0xFFF44336))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Ronda ${gameState.currentRound}/${gameState.totalRounds}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.weight(1f))

                if (!gameState.isGameFinished) {
                    Text(
                        text = "¡Elige tu jugada!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        GameChoice.values().forEach { choice ->
                            ChoiceButton(choice) {
                                if (!showResultDialog) {
                                    viewModel.playRound(choice)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
            }

            AnimatedVisibility(
                visible = showResultDialog,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    gameState.rounds.lastOrNull()?.let { lastRound ->
                        ResultCard(
                            playerChoice = lastRound.playerChoice,
                            iaChoice = lastRound.iaChoice,
                            result = lastRound.result
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ScoreDisplay(name: String, score: Int, color: Color) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF424242),
                textAlign = TextAlign.Center
            )
            Text(
                text = score.toString(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }

    @Composable
    fun ChoiceButton(choice: GameChoice, onClick: () -> Unit) {
        val scale by rememberInfiniteTransition(label = "").animateFloat(
            initialValue = 1f,
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            ),
            label = ""
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable(onClick = onClick)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(3.dp, Color(0xFF1976D2), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = choice.getEmoji(),
                    fontSize = 40.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = choice.getDisplayName(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }

    @Composable
    fun ResultCard(
        playerChoice: GameChoice,
        iaChoice: GameChoice,
        result: GameResult
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = when (result) {
                    GameResult.WIN -> Color(0xFF4CAF50)
                    GameResult.LOSE -> Color(0xFFF44336)
                    GameResult.DRAW -> Color(0xFFFF9800)
                }
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = when (result) {
                        GameResult.WIN -> "¡Has ganado!"
                        GameResult.LOSE -> "¡Has perdido!"
                        GameResult.DRAW -> "¡Empate!"
                    },
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = playerChoice.getEmoji(), fontSize = 64.sp)
                        Text(
                            text = "Tú",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Text(
                        text = "VS",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = iaChoice.getEmoji(), fontSize = 64.sp)
                        Text(
                            text = "IA",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
}