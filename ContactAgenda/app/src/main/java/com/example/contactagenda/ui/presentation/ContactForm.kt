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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.contactagenda.MainActivity
import com.example.contactagenda.data.repositories.contactRepository
import com.example.contactagenda.domain.entities.Contact
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun ContactForm(navController: NavController){
    var name by remember{ mutableStateOf("") }
    var tlf by remember{ mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

    val coroutine = rememberCoroutineScope()

    Column (){
        TextField(value = name,
            onValueChange = {newName -> name = newName})

        TextField(value = tlf,
            onValueChange = {newName -> tlf = newName})
        Row {
            Text("Otro")
            Checkbox(
                checked = gender == "O",
                onCheckedChange =
                {
                    checked = !checked
                    if(checked){
                        gender = "O"
                    }
                }

            )
            Text("Masculino")
            Checkbox(
                checked = gender == "M",
                onCheckedChange =
                    {
                        checked = !checked
                        if(checked){
                            gender = "M"
                        }
                    }

            )
            Text("Femenino")
            Checkbox(
                checked = gender == "F",
                onCheckedChange =
                    {
                        checked = !checked
                        if(checked){
                            gender = "F"
                        }
                    }

            )
        }
        Button(onClick = {
            coroutine.launch {
                val contact = Contact(id = 0 ,name=name, phoneNumber = tlf, gender = gender)
                MainActivity.database.ContactDao().addContact(contact)
            }
            navController.navigate("agenda")
        } ) {
            Text("Guardar")
        }
    }
}
