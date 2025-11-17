package com.example.laloteria.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random

@Composable
fun Resultado(navController: NavController, saldo: String, numApostado: String, apuesta: String) {
    val resultado = Random.nextInt(1,10)
    val num = numApostado.toInt()
    var nuevoSaldo: Int = saldo.toInt()
    if(apuesta.toInt() == resultado)
        nuevoSaldo = saldo.toInt()+numApostado.toInt()+numApostado.toInt()
    else
        nuevoSaldo = saldo.toInt()- numApostado.toInt()
    Column(Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center) {
        Text("${resultado}", fontSize = 30.sp)
        Text("${nuevoSaldo}")

        BotonVolver(navController, nuevoSaldo)
        BotonSalir(navController)
    }
}

@Composable
fun BotonVolver(navController: NavController, saldo: Int) {
    Button(onClick = {navController.navigate("eleccion/${saldo}")}) {
        Text("Volver a Jugar")
    }
}
@Composable
fun BotonSalir(navController: NavController) {
    Button(onClick = {navController.navigate("eleccion/${10}")}) {
        Text("Salir")
    }
}
