package com.example.elhotel.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elhotel.ui.viewmodel.HotelVM

@Composable
fun Login(navController: NavController, hotelViewModel: HotelVM) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Observamos el estado del usuario y de los mensajes
    val currentUser by hotelViewModel.currentUser.collectAsState()
    val uiState by hotelViewModel.uiState.collectAsState()

    // Si el usuario se loguea correctamente, navegamos a la pantalla principal
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            navController.navigate("Reservas") {
                // Limpiamos el historial para que no pueda volver al login con el botón atrás
                popUpTo("Login") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hotel App", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Nombre (solo necesario para registro, pero lo dejamos por tu estructura)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre (para registro)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Botón de Log In
            Button(
                onClick = { hotelViewModel.login(email) },
                enabled = email.isNotEmpty()
            ) {
                Text("Entrar")
            }

            // Botón de Registrar
            Button(
                onClick = { hotelViewModel.registrar(name, email) },
                enabled = email.isNotEmpty() && name.isNotEmpty()
            ) {
                Text("Registrarse")
            }
        }

        // Mostrar errores o mensajes de éxito si existen
        uiState?.let { mensaje ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = mensaje, color = MaterialTheme.colorScheme.primary)
        }
    }
}