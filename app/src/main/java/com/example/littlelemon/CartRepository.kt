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
        val trimmed = instructions.trim()
        val existing = cartItems.find {
            it.menuItem.id == item.id &&
                ((it.instructions.isNullOrBlank() && trimmed.isBlank()) ||
                    it.instructions == trimmed)
        }
        if (existing != null) {
            existing.quantity += 1
            val idx = cartItems.indexOf(existing)
            cartItems[idx] = existing.copy()
        } else {
            cartItems.add(
                CartItem(
                    item,
                    if (trimmed.isBlank()) null else trimmed,
                    1
                )
            )
        }
    }

    fun getItemCount(): Int = cartItems.sumOf { it.quantity }

    fun clear() {
        cartItems.clear()
    }

    fun removeItem(cartItem: CartItem) {
        cartItems.remove(cartItem)
    }
}

fun Double.formatDigits(digits: Int = 2): String = "%.${digits}f".format(this)
