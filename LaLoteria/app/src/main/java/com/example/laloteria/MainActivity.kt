package com.example.laloteria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laloteria.ui.theme.LaLoteriaTheme
import com.example.laloteria.ui.views.Apuesta
import com.example.laloteria.ui.views.Eleccion
import com.example.laloteria.ui.views.Resultado

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var saldo by remember { mutableStateOf(10) }
            val navController = rememberNavController()
            LaLoteriaTheme {
                NavHost(
                    navController = navController,
                    startDestination = "eleccion/${saldo}"
                ) {

                    composable("eleccion/{saldo}") { backStackEntry ->
                        val saldo = backStackEntry.arguments?.getString("saldo")
                        Eleccion(navController, saldo)
                    }
                    composable("apuesta/{num}/{saldo}") { backStackEntry ->
                        val numApuesta = backStackEntry.arguments?.getString("num")
                        val saldo = backStackEntry.arguments?.getString("saldo")
                        if (numApuesta != null && saldo != null)
                            Apuesta(navController, numApuesta, saldo)


                    }


                    composable("resultado/{numApuesta}/{saldo}/{apostado}") { backStackEntry ->
                        val apostado = backStackEntry.arguments?.getString("apostado")
                        val saldo = backStackEntry.arguments?.getString("saldo")
                        val numApuesta = backStackEntry.arguments?.getString("numApuesta")
                        if (apostado != null && saldo != null && numApuesta !=null)
                            Resultado(navController, saldo, apostado, numApuesta)
                    }
                }
            }
        }
    }
}

