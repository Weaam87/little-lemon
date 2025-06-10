package com.example.littlelemon.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.littlelemon.data.database.AppDatabase
import com.example.littlelemon.data.database.MenuItemDao
import com.example.littlelemon.data.network.MenuApi
import com.example.littlelemon.repository.MenuRepository
import com.example.littlelemon.ui.navigation.MyNavigation
import com.example.littlelemon.ui.theme.LittleLemonTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {

    private lateinit var db: AppDatabase
    private lateinit var menuDao: MenuItemDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = AppDatabase.getDatabase(applicationContext)
        menuDao = db.menuItemDao()

        lifecycleScope.launch {
            try {
                val menuItems = MenuApi.fetchMenu()
                MenuRepository.saveMenu(db, menuItems, this)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error occurred: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        setContent {
            LittleLemonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val menuItems by menuDao.getAll().observeAsState(emptyList())
                    MyNavigation(menuItems)
                }
            }
        }
    }



}

