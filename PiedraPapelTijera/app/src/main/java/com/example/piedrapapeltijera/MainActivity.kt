package com.example.piedrapapeltijera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.compravende.ui.viewmodel.PiedraPapelVM
import com.example.piedrapapeltijera.ui.theme.PiedraPapelTijeraTheme
import com.example.piedrapapeltijera.ui.views.Inicio
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val PPTiewModel: PiedraPapelVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            PiedraPapelTijeraTheme {
                NavHost(
                    navController = navController,
                    startDestination = "inicio"
                ) {

                    composable("inicio") {
                        Inicio(navController, PPTiewModel)
                    }
                    composable ("comprar/{id}"){backStackEntry ->
                        val idProduct = backStackEntry.arguments?.getInt("id")
                        if (idProduct != null){
                            Comprar(navController, CVViewModel, idProduct)
                        }

                    }
                    composable ("vender"){
                        Vender(navController, CVViewModel)
                    }

                }
            }
        }
    }
}

