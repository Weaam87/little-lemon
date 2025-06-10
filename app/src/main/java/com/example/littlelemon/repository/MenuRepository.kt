package com.example.littlelemon.repository

import com.example.littlelemon.data.database.AppDatabase
import com.example.littlelemon.data.database.MenuItemRoom
import com.example.littlelemon.data.network.MenuItemNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object MenuRepository {
    fun saveMenu(db: AppDatabase, items: List<MenuItemNetwork>, scope: CoroutineScope) {
        val entities = items.map { menuItem ->
            MenuItemRoom(
                id = menuItem.id,
                title = menuItem.title,
                description = menuItem.description,
                price = menuItem.price,
                image = menuItem.image,
                category = menuItem.category
            )
        }
        scope.launch(Dispatchers.IO) {
            db.menuItemDao().insertAll(entities)
        }
    }
}
