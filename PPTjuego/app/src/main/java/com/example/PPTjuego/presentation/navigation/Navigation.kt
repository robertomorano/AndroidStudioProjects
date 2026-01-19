package com.example.PPTjuego.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.PPTjuego.presentation.screen.game.GameScreen
import com.example.PPTjuego.presentation.screen.welcome.WelcomeScreen
import com.example.PPTjuego.presentation.screen.winner.WinnerScreen

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Game : Screen("game/{playerName}/{rounds}") {
        fun createRoute(playerName: String, rounds: Int) = "game/$playerName/$rounds"
    }
    object Winner : Screen("winner/{winner}/{playerName}") {
        fun createRoute(winner: String, playerName: String) = "winner/$winner/$playerName"
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onStartGame = { playerName, rounds ->
                    navController.navigate(Screen.Game.createRoute(playerName, rounds))
                }
            )
        }

        composable(Screen.Game.route) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            val rounds = backStackEntry.arguments?.getString("rounds")?.toIntOrNull() ?: 3

            GameScreen(
                playerName = playerName,
                totalRounds = rounds,
                onGameFinished = { winner ->
                    navController.navigate(Screen.Winner.createRoute(winner, playerName)) {
                        popUpTo(Screen.Welcome.route)
                    }
                }
            )
        }

        composable(Screen.Winner.route) { backStackEntry ->
            val winner = backStackEntry.arguments?.getString("winner") ?: ""
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""

            WinnerScreen(
                winner = winner,
                playerName = playerName,
                onPlayAgain = {
                    navController.navigate(Screen.Game.createRoute(playerName, 3)) {
                        popUpTo(Screen.Welcome.route)
                    }
                },
                onExit = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
    }
}