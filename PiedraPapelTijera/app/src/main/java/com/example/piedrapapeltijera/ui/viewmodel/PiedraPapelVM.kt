package com.example.compravende.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.compravende.data.repository.ProductRepository

class PiedraPapelVM : ViewModel() {
    private var nombre = mutableStateOf("")

    fun setName(nombre : MutableState<String>){
        this.nombre = nombre;
    }
}


