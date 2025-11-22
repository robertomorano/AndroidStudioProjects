package com.example.compravende.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compravende.domain.Product
import com.example.compravende.ui.viewmodel.CompraVentaVM



@Composable
fun Elegir1(navController: NavController, compraVentaVM: CompraVentaVM) {
    val productList by compraVentaVM.products
    val credits by compraVentaVM.credits

    // Usamos Surface como contenedor principal en lugar de Scaffold
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Título de la Pantalla
            Text(
                text = "Mercado de Productos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Sección de Créditos
            CreditsHeader(credits = credits)

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Productos
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Filtra solo los productos que NO están comprados
                items(productList.filter { !it.isBought }) { product ->
                    ProductItem(
                        product = product,
                        navController = navController,
                        compraVentaVM = compraVentaVM,
                        marketPrice = 0f
                    )
                }
            }
        }
    }
}

// --- COMPONENTES ---

@Composable
fun CreditsHeader(credits: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Créditos Actuales:",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "$${credits}",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}


@Composable
fun ProductItem(product: Product, navController: NavController, compraVentaVM: CompraVentaVM, marketPrice: Float) {
    var visibleActions by remember { mutableStateOf(false) }

    OutlinedCard(
        onClick = { visibleActions = !visibleActions },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Fila de Información Principal (Nombre y Precio)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Nombre del Producto
                Text(
                    text = product.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )

                // Precio
                Text(
                    text = "$${(product.price)}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )
            }

            // Animación de Visibilidad para los Botones (Acciones)
            AnimatedVisibility(visible = visibleActions) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    if (!product.isBought) {
                        BuyButton1(navController, product.id)
                    } else {
                        // El filtro en Elegir evita que se muestre, pero lo mantenemos por si acaso.
                        SellButton1(navController, product.id, compraVentaVM, marketPrice)
                    }
                }
            }
        }
    }
}


@Composable
fun BuyButton1(navController: NavController, id : Int) {
    Button(
        onClick = {
            navController.navigate("comprar/${id}")
        },
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
    ) {
        Text(
            text = "Comprar",
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun SellButton1(navController: NavController, id: Int, compraVentaVM: CompraVentaVM, marketPrice: Float) {
    OutlinedButton(
        onClick = {
            // Usar 'id' en lugar de 'product.id'
            compraVentaVM.cambiarBoolean(id)
            compraVentaVM.changeCredits(marketPrice)
            navController.navigate("elegir1")
        },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.error
        )
    ) {
        Text(
            text = "Vender",
            fontWeight = FontWeight.Bold,
        )
    }
}