package com.example.littlelemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OrderSummaryScreen(navController: NavHostController) {
    val items = CartRepository.cartItems
    var promoText by remember { mutableStateOf("") }
    var promoApplied by remember { mutableStateOf(false) }
    var showRemoveDialog by remember { mutableStateOf(false) }

    val subtotal = items.sumOf { it.menuItem.price.toDouble() * it.quantity }
    val discount = if (promoApplied) subtotal * 0.5 else 0.0
    val taxedSubtotal = subtotal - discount
    val tax = taxedSubtotal * 0.06
    val total = taxedSubtotal + tax

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
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = Color(0xFF495E57)
                )
            }
            BadgedBox(badge = {
                val count = CartRepository.getItemCount()
                if (count > 0) Badge { Text(count.toString()) }
            }) {
                Spacer(modifier = Modifier.size(0.dp))
            }
        }

        if (items.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = stringResource(id = R.string.cart),
                    modifier = Modifier.size(96.dp),
                    tint = Color(0xFF495E57)
                )
                Text(
                    text = stringResource(R.string.empty_cart),
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(0xFF495E57)
                )
                Text(
                    text = stringResource(R.string.empty_cart_hint),
                    color = Color(0xFF495E57)
                )
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(items) { cartItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlideImage(
                            model = cartItem.menuItem.image,
                            contentDescription = cartItem.menuItem.title,
                            modifier = Modifier
                                .size(80.dp)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(cartItem.menuItem.title)
                                Text("x${cartItem.quantity}")
                            }
                            Text("$${(cartItem.menuItem.price.toDouble() * cartItem.quantity).formatDigits()}")
                            cartItem.instructions?.let { instr ->
                                if (instr.isNotBlank()) {
                                    Text(text = instr)
                                }
                            }
                        }
                        IconButton(onClick = { CartRepository.removeItem(cartItem) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_item),
                                tint = Color(0xFF495E57)
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (!promoApplied) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = promoText,
                    onValueChange = { promoText = it },
                    modifier = Modifier.weight(1f),
                    label = { Text(stringResource(R.string.promo_code)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFF4CE14),
                        unfocusedBorderColor = Color(0xFF495E57),
                        focusedLabelColor = Color(0xFF495E57),
                        unfocusedLabelColor = Color(0xFF495E57),
                        focusedTextColor = Color(0xFF495E57),
                        unfocusedTextColor = Color(0xFF495E57)
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { if (promoText.equals("test", true)) promoApplied = true },
                    shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14))
                ) {
                    Text(stringResource(R.string.apply), color = Color.Black)
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.promo_code) + ": TEST")
                IconButton(onClick = { showRemoveDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = stringResource(R.string.remove),
                        tint = Color(0xFF495E57)
                    )
                }
            }
        }

        if (showRemoveDialog) {
            AlertDialog(
                onDismissRequest = { showRemoveDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        promoApplied = false
                        promoText = ""
                        showRemoveDialog = false
                    }) { Text(stringResource(R.string.remove)) }
                },
                dismissButton = {
                    TextButton(onClick = { showRemoveDialog = false }) { Text(stringResource(R.string.cancel)) }
                },
                text = { Text(stringResource(R.string.remove_promo_confirmation)) }
            )
        }

        Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            SummaryRow(stringResource(R.string.subtotal_label), "$${subtotal.formatDigits()}")
            if (promoApplied) {
                SummaryRow(stringResource(R.string.discount_label), "-$${discount.formatDigits()}")
            }
            SummaryRow(stringResource(R.string.tax_label), "$${tax.formatDigits()}")
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            SummaryRow(stringResource(R.string.total_label), "$${total.formatDigits()}", true)
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

@Composable
private fun SummaryRow(label: String, amount: String, bold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal)
        Text(amount, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal)
    }
}

