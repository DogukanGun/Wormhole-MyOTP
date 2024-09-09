package com.dag.check24products.base.bottomnavigation

import com.dag.check24products.R
import com.dag.check24products.base.navcontroller.NavScreen

enum class BarItem(var title:String,var icon:Int, var route:String){
    All("Alle", R.drawable.baseline_house, NavScreen.HomeScreen.route),
    Available("Verfügbar",R.drawable.baseline_available, NavScreen.PasskeyScreen.route),
    Favorites("Vorgemerkt",R.drawable.baseline_favorite, NavScreen.PasskeyScreen.route),
}
