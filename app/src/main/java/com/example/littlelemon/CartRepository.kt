package com.example.littlelemon

import androidx.compose.runtime.mutableStateListOf

data class CartItem(
    val menuItem: MenuItemRoom,
    var instructions: String? = null,
    var quantity: Int = 1
)

object CartRepository {
    val cartItems = mutableStateListOf<CartItem>()

    fun addItem(item: MenuItemRoom, instructions: String) {
        val existing = cartItems.find { it.menuItem.id == item.id }
        if (existing != null) {
            existing.quantity += 1
            if (instructions.isNotBlank()) {
                existing.instructions = instructions
            }
            val idx = cartItems.indexOf(existing)
            cartItems[idx] = existing.copy()
        } else {
            cartItems.add(CartItem(item, if (instructions.isBlank()) null else instructions, 1))
        }
    }

    fun getItemCount(): Int = cartItems.sumOf { it.quantity }

    fun clear() {
        cartItems.clear()
    }
}

fun Double.formatDigits(digits: Int = 2): String = "%.${digits}f".format(this)
