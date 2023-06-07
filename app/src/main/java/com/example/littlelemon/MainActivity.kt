package com.example.littlelemon

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
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {

    private lateinit var db: AppDatabase
    private lateinit var menuDao: MenuItemDao

    // Set up an HTTP client for Android using the HttpClient class.
    private val client = HttpClient(Android) {
        // Install content negotiation to handle request and response content types.
        install(ContentNegotiation) {
            // Configure JSON as the content type for requests and responses.
            json(contentType = ContentType("text", "plain"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = AppDatabase.getDatabase(applicationContext)
        menuDao = db.menuItemDao()

        lifecycleScope.launch {
            val menuItems = getMenu()
            saveMenuToDatabase(menuItems)
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

    //Fetch the menu
    private suspend fun getMenu(): List<MenuItemNetwork> {
        return try {
            val response: HttpResponse =
                client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            val menuNetwork: MenuNetwork = response.body()
            menuNetwork.menuItems
        } catch (e: Exception) {
            // checks if the current coroutine is already on the main thread
            // & Display a toast message with the exception details
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Error occurred: ${e.message}", Toast.LENGTH_LONG)
                    .show()
            }
            emptyList()
        }
    }


    //Store data in a Room database
    private fun saveMenuToDatabase(menuItems: List<MenuItemNetwork>) {
        // Transform each "MenuItemNetwork" object into a corresponding "MenuItemRoom" object.
        val menuEntities = menuItems.map { menuItem ->
            MenuItemRoom(
                id = menuItem.id,
                title = menuItem.title,
                description = menuItem.description,
                price = menuItem.price,
                image = menuItem.image,
                category = menuItem.category
            )
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val menuItemDao = db.menuItemDao() // Retrieve the MenuItemDao instance from AppDatabase
            menuItemDao.insertOrUpdateAll(menuEntities) // Use the updated insertOrUpdateAll function to insert or update the menu items
        }
    }

}
