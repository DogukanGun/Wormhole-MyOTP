package com.dag.myapplication.composebase.navcontroller

sealed class NavScreen(val route:String) {
    data object WelcomeScreen : NavScreen("welcome_screen")
    data object HomeScreen: NavScreen("home_screen")
    data object PasskeyScreen: NavScreen("passkey_screen")
    data object MainActivity: NavScreen("mainactivity_screen")

}

fun NavScreen.addData(data:Any):String{
    return this.route.plus(data)
}

fun NavScreen.addPath(path:String):String{
    return this.route.plus("/").plus(path)
}