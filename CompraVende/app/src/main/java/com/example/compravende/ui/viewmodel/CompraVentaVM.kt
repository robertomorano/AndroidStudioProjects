package com.example.compravende.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.compravende.data.repository.ProductRepository
import com.example.compravende.domain.Product

class CompraVentaVM : ViewModel() {

    private val _credits = mutableStateOf(10f)
    private val _repo = ProductRepository
    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> get() = _products

    val credits : State<Float> get() = _credits
    init {
        loadProducts()
    }

    private fun loadProducts() {
        _products.value = _repo.getProducts()
    }
    fun changeCredits(change: Float){
        if( _credits.value > -change){
            _credits.value += change
        }
    }
    fun cambiarBoolean(id: Int){
        var product = getProductById(id)
        product.isBought = !product.isBought
        _repo.delete(product)
        _repo.insert(product)
        loadProducts()
    }
    fun getProductById(id: Int): Product{
        var product =_products.value.find { it.id == id }
        if (product == null){
            product = _products.value[0]
        }
        return product
    }

    fun insertUser(user: Product){
        _repo.insert(user)
        loadProducts()
    }
}


