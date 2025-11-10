package com.example.boletin1jetopackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.boletin1jetopackcompose.ui.theme.Boletin1JetoPackComposeTheme

class Ej6Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Boletin1JetoPackComposeTheme {
                var visibility by remember { mutableStateOf(false) }
                var counter by remember { mutableStateOf(0) }
                var plus by remember { mutableStateOf(true) }
                Column(
                    modifier = Modifier.padding(30.dp).fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(counter.toString())
                    AnimatedVisibility (visible = visibility){
                        Text("Numero Maximo Alcanzado")
                    }
                    Button(onClick = {

                        if (counter >= 10 || counter < 0 ) {
                            visibility = true
                        }else{
                            visibility = false
                            if (plus) counter = counter + 1 else counter--
                        }
                    }) {
                        Text(if (plus) "+" else "-")
                    }
                    Button(onClick = { plus = !plus }) {
                        Text("+/-")
                    }
                    Button(onClick = { counter = 0 }) {
                        Text("Reiniciar")
                    }
                }


            }
        }
    }
}