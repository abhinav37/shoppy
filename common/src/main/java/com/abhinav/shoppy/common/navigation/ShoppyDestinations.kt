package com.abhinav.shoppy.common.navigation

/*
* Simple interface for keeping all routes in a easy visible place
*/
interface ShoppyDestinations {
    val route: String
}

object Home : ShoppyDestinations {
    override val route = "home"
}

object Details : ShoppyDestinations {
    const val ARG_PRODUCT_ID = "taskId"
    override val route = "details"
}
