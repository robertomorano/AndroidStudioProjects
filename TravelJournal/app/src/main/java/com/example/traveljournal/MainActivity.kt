package com.example.traveljournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.traveljournal.domain.entities.Travel

import com.example.traveljournal.ui.theme.TravelJournalTheme
import com.example.traveljournal.viewmodels.showTravelsVM
import kotlin.getValue




class MainActivity : ComponentActivity() {
    private val travelViewModel: showTravelsVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TravelJournalTheme {

                    Ej1HomeScreen(showTravelsVaM=travelViewModel)

            }
        }
    }
}



@Composable
fun Ej1HomeScreen(showTravelsVaM: showTravelsVM = viewModel()
) {


    val list by showTravelsVaM.travelList

    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(list) { itemUser ->
            Row{
                Card {
                    Column { Text(itemUser.city.toString()) }
                    Column { Text(itemUser.country.toString()) }
                }
            }

        }
    }

}