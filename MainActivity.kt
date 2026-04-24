
package com.foodtruck.pos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Product(val name: String, val price: Double)
data class CartItem(val product: Product, var qty: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodTruckApp()
        }
    }
}

@Composable
fun FoodTruckApp() {
    val products = remember {
        mutableStateListOf(
            Product("Hamburguesa", 5.5),
            Product("Hot Dog", 3.0),
            Product("Papas", 2.5),
            Product("Soda", 1.5)
        )
    }
    val cart = remember { mutableStateListOf<CartItem>() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("FoodTruck POS", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(products) { product ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(product.name)
                    Text("$${product.price}")
                    Button(onClick = {
                        val item = cart.find { it.product == product }
                        if (item != null) item.qty++
                        else cart.add(CartItem(product, 1))
                    }) {
                        Text("Agregar")
                    }
                }
            }
        }

        Divider()

        Text("Carrito")

        cart.forEach {
            Text("${it.product.name} x${it.qty} = $${it.product.price * it.qty}")
        }

        val total = cart.sumOf { it.product.price * it.qty }

        Text("Total: $${total}")

        Button(
            onClick = { cart.clear() },
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        ) {
            Text("Cobrar")
        }
    }
}
