package com.example.boletin1jetopackcompose

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boletin1jetopackcompose.ui.theme.Boletin1JetoPackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Boletin1JetoPackComposeTheme {
                var texto by remember { mutableStateOf("¡Hola, desconocido!") }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(){
                        Greeting(
                            name = texto,
                            modifier = Modifier.padding(innerPadding)
                        )
                        Button(onClick = {texto = "pulsodo boton"


                        }) {Text(
                            text = "Púlsame",
                            fontSize = 18.sp, // Tamaño de fuente
                            color = Color.White
                        )   }
                        ButtonIntent("Ej2","Ej2Activity")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun ButtonIntent(texto: String ,goto: String){
    val contexto = LocalContext.current
    Button(onClick = {
        val intent = Intent(contexto, Ej2Activity::class.java)
        contexto.startActivity(intent)
    }) {Text(
        text = texto,
        fontSize = 18.sp, // Tamaño de fuente
        color = Color.White
    )   }
}

