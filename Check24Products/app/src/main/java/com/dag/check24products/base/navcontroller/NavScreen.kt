package com.dag.check24products.base.navcontroller

sealed class NavScreen(val route:String) {
    data object HomeScreen: NavScreen("home_screen")
    data object AvailableScreen: NavScreen("available_screen")
    data object FavoritesScreen: NavScreen("favorites_screen")
    data object MainActivity: NavScreen("main_activity")

}

fun NavScreen.addData(data:Any):String{
    return this.route.plus(data)
}

fun NavScreen.addPath(path:String):String{
    return this.route.plus("/").plus(path)
}