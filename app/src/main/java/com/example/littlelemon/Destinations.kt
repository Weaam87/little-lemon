package com.example.littlelemon

interface Destinations {
    val route : String
}

object Home : Destinations {
    override val route = "Home"
}

object Profile : Destinations {
    override val route = "Profile"
}

object Onboarding  : Destinations {
    override val route = "Onboarding"
}

object MenuItemDetail : Destinations {
    override val route = "Detail"
    const val idArg = "itemId"
}

object OrderSummary : Destinations {
    override val route = "OrderSummary"
}

