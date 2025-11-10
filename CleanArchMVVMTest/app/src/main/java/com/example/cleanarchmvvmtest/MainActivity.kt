package com.example.cleanarchmvvmtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cleanarchmvvmtest.ui.theme.CleanArchMVVMTestTheme
import com.example.cleanarchmvvmtest.ui.view.UserListScreen
import com.example.cleanarchmvvmtest.ui.viewmodel.UserVM
import kotlin.getValue

class MainActivity : ComponentActivity() {
    // ViewModel compartido para toda la actividad
    private val userViewModel: UserVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CleanArchMVVMTestTheme {
                UserListScreen(userViewModel=userViewModel)
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CleanArchMVVMTestTheme {
        Greeting("Android")
    }
}
