package com.example.compravende.data.repository

object ProductRepository {
    val list = mutableListOf<Product>(
        Product(1, "ubai", 2, false),
        Product(2, "daso",2, false),
        Product(3, "cinco",2, false),
        Product(4, "wdwwa",2, false),
        Product(5, "adw",2, false),

        )
    fun getProducts(): List<Product> {
        return list.toList()
    }
    fun delete(product: Product) {
        list.removeIf { product.id == it.id }
    }

    fun insert(product: Product) { list.add(product) }
}