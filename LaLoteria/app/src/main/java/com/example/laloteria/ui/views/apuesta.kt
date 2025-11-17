package com.example.laloteria.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Apuesta(navController: NavController, numApuesta: String, saldo: String) {
    val num = numApuesta.toInt()
    var apuesta by remember { mutableStateOf(0) }
    var plus by remember { mutableStateOf(true) }

    Row(Modifier
        .fillMaxSize()
        .padding(30.dp),
        Arrangement.Center
        ) {
        BotonApostar(navController, apuesta, saldo.toInt(), apostado = numApuesta)
    }
    Row(Modifier
        .fillMaxSize()
        .padding(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Column(
            modifier = Modifier
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(apuesta.toString())

            Button(onClick = {

                if (apuesta >= saldo.toInt() || apuesta < 0) {
                    apuesta = 0
                } else {
                    if (plus) apuesta = apuesta + 1 else apuesta--
                }
            }) {
                Text(if (plus) "+" else "-")
            }
            Button(onClick = { plus = !plus }) {
                Text("+/-")
            }
            Button(onClick = { apuesta = 0 }) {
                Text("Reiniciar")
            }
        }

        MostrarSaldo(saldo.toInt())
    }

}

@Composable
fun BotonApostar(navController: NavController, numApuesta: Int, saldo: Int, apostado: String) {
    Button(onClick = { navController.navigate("resultado/${numApuesta}/${saldo}/${apostado}") })
    {
        Text("Apostar")
    }
}

@Composable
fun MostrarSaldo(saldo: Int?) {
    Row(
        Modifier

            .padding(30.dp),
        horizontalArrangement = Arrangement.End,
        Alignment.CenterVertically
    ) {
        Text("Saldo: ${saldo}")
    }
}

