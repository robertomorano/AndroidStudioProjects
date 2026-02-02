package com.example.elhotel.domain.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.elhotel.data.repository.HotelRepository
import com.example.elhotel.ui.viewmodel.HotelVM

class HotelVMFactory(private val repository: HotelRepository):ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HotelVM(repository) as T // <--- Aquí ocurre la magia
        }
    }