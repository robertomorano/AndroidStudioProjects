package com.example.test

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.ActivityWelcomeBinding
import androidx.core.net.toUri

class WelcomeActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val binding = ActivityWelcomeBinding.inflate(layoutInflater)
            Log.d("TAG", "onCreate(1) creado")


            setContentView(binding.root)
            binding.btnHome.setOnClickListener {
                val intentHome = Intent(this, MainActivity::class.java)
                startActivity(intentHome)
            }
            val userName= intent.getStringExtra("userName").toString()
            binding.txtWelcome.text = "Bienvenido, " + userName
            binding.btnDial.setOnClickListener{
                val tlfNum = binding.inputUser.text.toString()
                sendToDial(tlfNum)
            }
            binding.btnInternet.setOnClickListener{
                val search = binding.inputUser.text.toString()
                navigate(search)
            }
            binding.btnMessage.setOnClickListener{
                val msg = binding.inputUser.text.toString()
                messageSomeone(msg)
            }
            binding.btnShare.setOnClickListener{
                val shareTo = binding.inputUser.text.toString()
                share(shareTo)
            }
        }
        private fun sendToDial(text: String){
            val intentCall = Intent(Intent.ACTION_DIAL,"tel:${text}".toUri())
            startActivity(intentCall)
        }

        private fun navigate(text: String){
            val webpage: Uri = Uri.parse("https://www.google.com/search?q=${text}.")
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(intent)

        }

        private fun messageSomeone(text: String){
            val smsIntent = Intent(Intent.ACTION_VIEW)
            smsIntent.data = Uri.parse("smsto:" + text)
            smsIntent.putExtra("sms_body", "Hola desde mi app")
            startActivity(smsIntent)

        }

        private fun share(text: String){
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(shareIntent, "Compartir con..."))

        }
    override fun onStart(){
        super .onStart()


        Log.d("TAG", "onStart(1) llamado")

    }

    override fun onRestart() {
        super.onRestart()


        Log.d("TAG", "onRestart(1) llamado - Volviendo de estar 'stopped'")

    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, getString(R.string.toast_actividad_b), Toast.LENGTH_SHORT).show()

        Log.d("TAG", "onResume(1) llamado - ¡La Activity es visible y activa!")

    }

    override fun onPause() {
        super.onPause()

        Log.d("TAG", "onPause(1) llamado - Otra Activity toma el foco")

    }

    override fun onStop() {
        super.onStop()

        Log.d("TAG", "onStop(1) llamado - La Activity ya no es visible")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("TAG", "onDestroy(1) llamado - La Activity está siendo destruida")
    }
}
