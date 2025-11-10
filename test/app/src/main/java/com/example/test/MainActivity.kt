package com.example.test

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.net.toUri
import com.example.test.databinding.ActivityMainBinding
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        Log.d("TAG", "onCreate() creado")
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val myIntent = Intent(applicationContext, WelcomeActivity::class.java)
            val UserName = binding.UserInput.text.toString()
            if ( UserName == "admin" && binding.PasswordInput.text.toString() == "admin") {
                myIntent.putExtra("userName", UserName)
                startActivity(myIntent)

            }else{
                val toast = Toast.makeText(
                    applicationContext,
                    "¡Error Contraseña o Usuario!",
                    Toast.LENGTH_SHORT).show()

            }
        }
    }
    override fun onStart(){
        super .onStart()


        Log.d("TAG", "onStart() llamado")

    }

    override fun onRestart() {
        super.onRestart()


        Log.d("TAG", "onRestart() llamado - Volviendo de estar 'stopped'")

    }

    override fun onResume() {
        super.onResume()

        Log.d("TAG", "onResume() llamado - ¡La Activity es visible y activa!")

    }

    override fun onPause() {
        super.onPause()

        Log.d("TAG", "onPause() llamado - Otra Activity toma el foco")

    }

    override fun onStop() {
        super.onStop()

        Log.d("TAG", "onStop() llamado - La Activity ya no es visible")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("TAG", "onDestroy() llamado - La Activity está siendo destruida")
    }
}