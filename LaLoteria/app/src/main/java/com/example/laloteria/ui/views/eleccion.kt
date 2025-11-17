package com.example.laloteria.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Eleccion(navController: NavController, saldo: String?) {
    var saldoN : Int? = saldo?.toInt()
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text("Apuesta", fontSize = 20.sp)
        Text("Selecciona tu apuesta")

        BotonPosicion(navController, saldoN)
    }
}

@Composable
fun BotonApuesta(num: Int, saldo: Int?, navController: NavController) {
    Button(
        onClick = { navController.navigate("apuesta/${num}/${saldo}") }
    ) {
        Text(num.toString())
    }
}

@Composable
fun BotonPosicion(navController: NavController, saldo: Int?) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Row {
            BotonApuesta(1,saldo, navController )
            BotonApuesta(2, saldo,navController)
            BotonApuesta(3, saldo,navController)
        }
        Row {
            BotonApuesta(4, saldo,navController)
            BotonApuesta(5, saldo,navController)
            BotonApuesta(6, saldo,navController)
        }
        Row {
            BotonApuesta(7, saldo,navController)
            BotonApuesta(8, saldo,navController)
            BotonApuesta(9, saldo,navController)
        }
    }
}