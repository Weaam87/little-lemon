package com.example.littlelemon.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

object MenuApi {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    suspend fun fetchMenu(): List<MenuItemNetwork> {
        val response: HttpResponse =
            client.get("https://raw.githubusercontent.com/Weaam87/App-capstone-data/main/menu.json")
        val menuNetwork: MenuNetwork = response.body()
        return menuNetwork.menuItems
    }
}
