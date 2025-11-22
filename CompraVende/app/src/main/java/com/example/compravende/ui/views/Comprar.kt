package com.example.compravende.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compravende.domain.Product
import com.example.compravende.ui.viewmodel.CompraVentaVM

@Composable
fun Comprar(navController: NavController, compraVentaVM: CompraVentaVM, idProduct: Int) {
    val product = compraVentaVM.getProductById(idProduct)
    val marketPrice = product.price + (product.price * 0.2f)

    Column(
        modifier = Modifier
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier

                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Creditos: " + compraVentaVM.credits.value.toString(),
                fontWeight = FontWeight.Bold,

                )
        }
        Row(
            modifier = Modifier


                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            MostrarProducto(product, marketPrice)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()

                .padding(16.dp),
        ) {
            Column {
                ConfirmButton(navController, compraVentaVM, marketPrice, product)
            }
            Column {
                CancelButton(navController)
            }

        }
    }
}

@Composable
fun MostrarProducto(product: Product, marketPrice: Float) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(

            Modifier
                .fillMaxWidth()
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

            Text(text = "Precio de Mercado: $marketPrice")
        }
    }
}

@Composable
fun ConfirmButton(navController: NavController, compraVentaVM: CompraVentaVM, marketPrice: Float, product: Product) {
    Button(
        onClick = {
            compraVentaVM.changeCredits(-marketPrice)
            compraVentaVM.cambiarBoolean(product.id)
            navController.navigate("vender")
        }
    ) {
        Text(
            text = "Confirmar",
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun CancelButton(navController: NavController) {
    Button(
        onClick = {

            navController.navigate("elegir1")
        }
    ) {
        Text(
            text = "Cancelar",
            fontWeight = FontWeight.Bold,
        )
    }
}