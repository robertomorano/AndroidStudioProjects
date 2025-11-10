package com.example.boletin1jetopackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.boletin1jetopackcompose.ui.theme.Boletin1JetoPackComposeTheme

class Ej5Activity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Boletin1JetoPackComposeTheme {

                var visibility by remember { mutableStateOf(false) }
                    Card (
                        modifier = Modifier.padding(30.dp)

                    ){
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            "imagen"
                        )
                        Column(
                            modifier = Modifier.padding(30.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnimatedVisibility(visible = visibility) {
                                Column () {
                                    Text(
                                        text = "Nombre: Nombre"
                                    )
                                    Text(
                                        text = "Profesion: Nini"
                                    )
                                    Text(
                                        text = "Email: a@b.c"
                                    )
                                }
                            }

                        }
                        Button(onClick = {

                                visibility = !visibility


                        }) {
                            Text(if(!visibility) "Ver Mas" else "VerMEnos")

                        }

                    }



            }
        }
    }
}