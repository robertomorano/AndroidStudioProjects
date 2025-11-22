package com.example.compravende.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compravende.ui.viewmodel.CompraVentaVM

@Composable
fun Vender(navController: NavController, compraVentaVM: CompraVentaVM){
    val productList by compraVentaVM.products

    Column(modifier = Modifier

        .padding(16.dp),) {
        Row(
            modifier = Modifier.fillMaxWidth()

                .padding(16.dp),
        ) {
            Text(
                text = "Creditos: " + compraVentaVM.credits.value.toString(),
                fontWeight = FontWeight.Bold,

                )
        }
        Row(
            modifier = Modifier

                .padding(16.dp),
        ) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(productList) {

                        product ->
                    if (product.isBought) {
                        val marketPrice = product.price + (product.price * 0.2f)

                        ProductItem(product, navController, compraVentaVM,marketPrice)

                    }
                }
            }
        }

    }
}
