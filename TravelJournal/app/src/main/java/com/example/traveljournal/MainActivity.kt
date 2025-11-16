package com.example.traveljournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.traveljournal.ui.theme.TravelJournalTheme
import com.example.traveljournal.ui.view.Setting
import com.example.traveljournal.ui.view.TravelDetail
import com.example.traveljournal.viewmodels.ShowTravelsVM
import kotlin.getValue




class MainActivity : ComponentActivity() {
    private val travelViewModel: ShowTravelsVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            var isDarkMode by remember { mutableStateOf(false) }
            TravelJournalTheme {


                NavHost(
                    navController = navController,
                    startDestination = "travelList"
                ) {

                    composable("travelList") {Ej1HomeScreen(showTravelsVM=travelViewModel, navController)}
                    composable("detail/{idTravel}" ) {
                            backStackEntry ->
                        val idTravel = backStackEntry.arguments?.getString("idTravel")
                        TravelDetail(navController, idTravel, travelViewModel)
                    }


                    composable ("settings/{isDarkMode}"){ Setting(navController) }
                }
            }
        }
    }
}



@Composable
fun Ej1HomeScreen(showTravelsVM: ShowTravelsVM = viewModel(), navController: NavController
) {


    val list by showTravelsVM.travelList

    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(list) { itemTravel ->
            Row{
                Card(onClick = { navController.navigate("detail/{idTravel}") }) {
                    Column { Text(itemTravel.city.toString()) }
                    Column { Text(itemTravel.country.toString()) }
                }
            }

        }
    }

}