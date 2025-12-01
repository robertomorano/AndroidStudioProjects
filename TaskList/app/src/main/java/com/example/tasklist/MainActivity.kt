package com.example.tasklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.tasklist.data.database.TaskDatabase
import com.example.tasklist.domain.entities.TaskEntity
import com.example.tasklist.ui.theme.TaskListTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    companion object{
        lateinit var database: TaskDatabase
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            this,
            TaskDatabase::class.java,
            "task-db"
        ).build()
        enableEdgeToEdge()
        setContent {


            val navController = rememberNavController()



            TaskListTheme {
                NavHost(
                    navController = navController,
                    startDestination = "elegir"
                ) {

                    composable("elegir") {
                        Inicio(navController)
                    }

            }
        }
    }
}
@Composable
fun Inicio(navController: NavController){
    var taskList = remember {   mutableStateListOf<TaskEntity>()}
    LaunchedEffect(Unit) {
        taskList.clear()
        taskList.addAll(database.taskDao().getAllTask())

    }
    var newTask by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Row (Modifier.fillMaxWidth(), Arrangement.Center){
            TextField(value = newTask, onValueChange = {newTask = it})
            Button(onClick = {
                coroutineScope.launch {
                    database.taskDao().addTask(TaskEntity( name = newTask))


                }
                navController.navigate("elegir")
            }) {
                Text("+")
            }

        }
        Row (Modifier.fillMaxWidth(), Arrangement.Center){
            LazyColumn () {
                items(taskList){
                        task -> var checked by remember { mutableStateOf(false) }
                    Row {
                        Checkbox(checked = checked,
                            onCheckedChange = {checkedStatus -> checked = checkedStatus})
                        Text(task.name)
                    }
                }
            }
        }
    }
}
}
