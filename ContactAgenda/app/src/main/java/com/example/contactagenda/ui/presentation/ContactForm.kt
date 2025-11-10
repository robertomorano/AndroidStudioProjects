package com.example.contactagenda.ui.presentation



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@Composable
fun ContactForm(navController: NavController){
    var name by remember{ mutableStateOf("") }
    var tlf by remember{ mutableStateOf("") }
    var gender by remember { mutableStateOf(0) }
    var checked by remember { mutableStateOf(false) }
    Column (){
        TextField(value = name,
            onValueChange = {newName -> name = newName})

        TextField(value = tlf,
            onValueChange = {newName -> tlf = newName})
        Row {
            Text("")
            Checkbox(
                checked = gender == 0,
                onCheckedChange =
                {
                    checked = !checked
                    if(checked){
                        gender = 0
                    }
                }

            )
            Checkbox(
                checked = gender == 1,
                onCheckedChange =
                    {
                        checked = !checked
                        if(checked){
                            gender = 1
                        }
                    }

            )
            Checkbox(
                checked = gender == 2,
                onCheckedChange =
                    {
                        checked = !checked
                        if(checked){
                            gender = 2
                        }
                    }

            )
        }
        /*Button(onClick = ) {
            Text("Guardar")
        }*/
    }
}
