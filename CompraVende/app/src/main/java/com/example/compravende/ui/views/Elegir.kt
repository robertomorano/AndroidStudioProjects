package com.example.compravende.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compravende.domain.Product
import com.example.compravende.ui.viewmodel.CompraVentaVM

@Composable
fun Elegir(navController: NavController, compraVentaVM: CompraVentaVM) {
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
                    if (!product.isBought) {
                        ProductRow(product, navController, compraVentaVM, marketPrice = 0f)
                    }
                }
            }
        }
    }

}



@Composable
fun ProductRow(product: Product, navController: NavController, compraVentaVM: CompraVentaVM, marketPrice: Float) {
    var visiblePrice by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            onClick = {
                visiblePrice = !visiblePrice
            },
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            /*Text(
                text = product.id.toString(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(30.dp)
            )*/
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = product.name)

            Spacer(modifier = Modifier.width(8.dp))
            Text(text = product.price.toString())
            AnimatedVisibility(visible = visiblePrice) {
                if (!product.isBought) {
                    BuyButton(navController, product.id)
                }else{
                    SellButton(navController, product.id, compraVentaVM, marketPrice)
                }
            }
        }


    }

}


@Composable
fun BuyButton(navController: NavController, id : Int) {
    Button(onClick = {
        navController.navigate("comprar/${id}")
    }) {
        Text(
            text = "Comprar",
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun SellButton(navController: NavController, id: Int, compraVentaVM: CompraVentaVM, marketPrice: Float) {
    Button(onClick = {
        compraVentaVM.cambiarBoolean(id)
        compraVentaVM.changeCredits(marketPrice)
        navController.navigate("elegir")

    }) {
        Text(
            text = "Vender",
            fontWeight = FontWeight.Bold,
        )
    }
}





