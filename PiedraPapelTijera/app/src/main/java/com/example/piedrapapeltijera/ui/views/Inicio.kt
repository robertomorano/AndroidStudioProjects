package com.example.piedrapapeltijera.ui.views



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compravende.ui.viewmodel.PiedraPapelVM
import kotlinx.coroutines.launch

@Composable
fun Inicio(navController: NavController, PPTViewModel: PiedraPapelVM){

    var newTask by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Row (Modifier.fillMaxWidth(), Arrangement.Center){
            TextField(value = newTask, onValueChange = {newTask = it})
            Button(onClick = {
                PPTViewModel.setName(newTask as MutableState<String>)
                navController.navigate("juego")



            }) {
                Text("Jugar")
            }

        }

        }
    }
}