package com.example.boletin1jetopackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.boletin1jetopackcompose.ui.theme.Boletin1JetoPackComposeTheme

class Ej3Activity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Boletin1JetoPackComposeTheme {

                val frases = listOf("Sigue adelante", "Nunca te rindas", "El código es poesía",
                    "Aprende algo nuevo hoy")
                var frase  by remember { mutableStateOf(frases[0]) }
                    Column(modifier = Modifier.padding(30.dp).fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                    {
                        Text(frase)
                        Button(onClick = {frase = frases.random()}) {
                            Text("Cambia Frase")
                        }
                    }


            }
        }
    }
}
