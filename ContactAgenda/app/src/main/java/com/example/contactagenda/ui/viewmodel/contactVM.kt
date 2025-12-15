package com.example.contactagenda.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

class contactVM : ViewModel() {
       /* // Expone los datos como StateFlow
        val allItems: StateFlow<List<Contact>> = repository.getAllItems()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

        // Función para operaciones de escritura
        fun insertItem(item: Item) = viewModelScope.launch {
            repository.insertItem(item)
        }*/

    }