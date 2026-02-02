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
import androidx.compose.material3.TextButton
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
fun Registrar(navController: NavController, hotelViewModel: HotelVM) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Observamos si se crea el usuario para navegar al Home
    val currentUser by hotelViewModel.currentUser.collectAsState()
    val uiState by hotelViewModel.uiState.collectAsState()

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            navController.navigate("Home") {
                popUpTo("Registrar") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Crea tu cuenta", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Botón principal de Registro
        Button(
            onClick = {
                if (name.isNotBlank() && email.isNotBlank()) {
                    hotelViewModel.registrar(name, email)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotBlank() && email.isNotBlank()
        ) {
            Text("Registrarse")
        }

        // Botón para volver atrás si ya tienes cuenta
        TextButton(onClick = { navController.popBackStack() }) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }

        // Feedback de errores (ej: email duplicado)
        uiState?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}