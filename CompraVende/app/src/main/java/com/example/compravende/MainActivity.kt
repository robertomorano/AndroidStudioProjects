package com.example.compravende

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compravende.ui.theme.CompraVendeTheme
import com.example.compravende.ui.viewmodel.CompraVentaVM
import com.example.compravende.ui.views.Comprar
import com.example.compravende.ui.views.Elegir
import com.example.compravende.ui.views.Elegir1
import com.example.compravende.ui.views.Vender
import kotlin.getValue



class MainActivity : ComponentActivity() {

    private val CVViewModel: CompraVentaVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            CompraVendeTheme {
                NavHost(
                    navController = navController,
                    startDestination = "elegir1"
                ) {

                    composable("elegir1") {
                        Elegir1(navController, CVViewModel)
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



