package com.example.contactagenda.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.contactagenda.MainActivity
import com.example.contactagenda.R
import com.example.contactagenda.data.database.ContactDatabase

import com.example.contactagenda.domain.entities.Contact
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ContactRow(contacto: Contact) {
    var visibility by remember { mutableStateOf(false) }
    var id = 0
    if (contacto.gender == "H") {
        id = R.drawable.ic_launcher_foreground
    } else {
        id = R.drawable.ic_launcher_background
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Row {
            Image(
                painter = painterResource(id),
                contentDescription = "Foto contacto",
                Modifier
                    .height(100.dp)
                    .clickable { visibility = !visibility }
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Column {
                if (visibility) {
                    ContactRowAll(contacto)
                } else {
                    ContactRowCapLet(contacto)
                }

            }
        }
    }

}

@Composable
fun ContactRowAll(contacto: Contact) {
    Text(
        text = contacto.name,
        fontSize = 24.sp,
        modifier = Modifier.padding(8.dp)
    )
    Text(
        text = contacto.phoneNumber,
        fontSize = 24.sp,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun ContactRowCapLet(contacto: Contact) {

    Text(
        text = getCapital(contacto.name),
        fontSize = 24.sp,
        modifier = Modifier.padding(8.dp)
    )


}

fun getCapital(name: String): String {
    return name.filter { it.isUpperCase() }
}


@Composable
fun ContactsScreen(navController: NavController, modifier: Modifier = Modifier) {
    val list = remember { mutableStateListOf<Contact>() }

    LaunchedEffect(Unit) {
        val listDB = MainActivity.database.ContactDao().getAllContact()
        list.clear()
        list.addAll(listDB)
    }


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {    // Scroll vertical,
            items(list) { itemContact ->


                ContactRow(itemContact)


            }

        }
        FloatingActionButton(
            onClick = {
                navController.navigate("add")
            }
        ) { Text("+") }
    }
}



