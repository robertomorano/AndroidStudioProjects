package com.example.compravende.domain

data class Product(
    val id:Int,
    val name: String,
    val price: Int,
    var isBought : Boolean
){}
