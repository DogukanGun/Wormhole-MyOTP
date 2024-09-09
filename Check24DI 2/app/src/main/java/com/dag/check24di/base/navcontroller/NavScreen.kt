package com.dag.check24di.base.navcontroller

sealed class NavScreen(val route:String) {
    data object HomeScreen: NavScreen("home_screen")
    data object WebView: NavScreen("web_view")
    data object ProductScreen: NavScreen("product_screen")
    data object MainActivity: NavScreen("main_activity")

}


fun NavScreen.addData(data:Any):String{
    return this.route.plus(data)
}

fun NavScreen.addPath(path:String):String{
    return this.route.plus("/").plus(path)
}