package com.example.ejemplojetc

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ejemplojetc.ui.theme.EjemploJetCTheme
import kotlin.contracts.SimpleEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            EjemploJetCTheme {

                    Login()

            }
        }
    }
}

@Composable
fun Greeting(txt: String, modifier: Modifier = Modifier) {
    Row{
        Button(onClick = {TODO()}) { }
        Text(

        text = "Hello $txt!",
        modifier = modifier
        )
        Text(

        text = "Hello $txt!",
        modifier = modifier
        )
    }
}
@Composable
fun Login(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        LoginText("Log In")
        LoginLogic()
        BasicButton()
    }

}
@Composable
fun LoginText(txt: String, modifier: Modifier = Modifier) {

        Image(
            painter = painterResource(id= R.drawable.ic_launcher_foreground),
            contentDescription = "agusulu"

        )
        Text(
            text = txt, fontSize = 48.sp, modifier = Modifier.padding(8.dp),
            color = Color(255, 0, 0, 255)
        )




}

@Composable
fun BasicButton(){
    val context= LocalContext.current
    Button(
        onClick = {       Toast.makeText(context,"HOLA",Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White
        )
    ) {
        // Texto del botón
        Text(
            text = "Púlsame",
            fontSize = 18.sp, // Tamaño de fuente
            color = Color.White
        )
    }

}
@Composable
fun SimpleTextField() {
    var text by remember {
        mutableStateOf(TextFieldValue(""))
    }
    TextField(
        value = text,
        onValueChange = {
            text = it		// Lo que se escriba en ese momento
        }
        //label = {Text(placeHolder)}
    )
}
@Composable
fun PasswordTextField(){
    var password by remember { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Contraseña") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )

}
@Composable
fun LoginLogic(){
    SimpleTextField()
    PasswordTextField()
}


/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EjemploJetCTheme {
        Greeting("Android")
    }
}*/