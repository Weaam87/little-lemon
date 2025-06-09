package com.example.littlelemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun OrderSummaryScreen(navController: NavHostController) {
    val items = CartRepository.cartItems
    val subtotal = items.sumOf { it.menuItem.price.toDouble() * it.quantity }
    val tax = subtotal * 0.06
    val total = subtotal + tax

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = Color(0xFF495E57)
                )
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items) { cartItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(cartItem.menuItem.title)
                    Text("x${cartItem.quantity}")
                    Text("$${(cartItem.menuItem.price.toDouble() * cartItem.quantity).formatDigits()}")
                }
                cartItem.instructions?.let {
                    if (it.isNotBlank()) {
                        Text(text = it, modifier = Modifier.padding(start = 16.dp))
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            Text(text = stringResource(R.string.subtotal, subtotal.formatDigits()))
            Text(text = stringResource(R.string.tax, tax.formatDigits()))
            Text(text = stringResource(R.string.total, total.formatDigits()))
        }

        Button(
            onClick = {
                CartRepository.clear()
                navController.popBackStack(Home.route, false)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14))
        ) {
            Text(stringResource(R.string.checkout), color = Color.Black)
        }
    }
}

