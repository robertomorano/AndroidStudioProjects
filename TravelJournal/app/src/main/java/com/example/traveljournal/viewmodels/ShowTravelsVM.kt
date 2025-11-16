package com.example.traveljournal.viewmodels

import android.R
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.traveljournal.data.repositories.travelRepository
import com.example.traveljournal.domain.entities.Travel

class ShowTravelsVM() : ViewModel() {
    private val _repo = travelRepository
    private val _travels = mutableStateOf<List<Travel>>(emptyList())
    val travelList: State<List<Travel>> get() = _travels

    init {
        loadUsers()
    }

    private fun loadUsers() {
        _travels.value = _repo.getAllTravels()
    }
    public fun getTravelById(id: Int): Travel? {
        return travelList.value.find { it.id == id }
    }
}