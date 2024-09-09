 package com.dag.check24di

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.dag.check24di.base.navcontroller.NavGraph
import com.dag.check24di.feature.home.HomeVM
import com.dag.check24di.feature.home.HomeView
import com.dag.check24di.ui.theme.Check24DITheme
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject

 class MainActivity: ComponentActivity() {
     val homeVM: HomeVM by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Check24DITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   NavGraph(innerPadding = innerPadding)
                }
            }
        }
    }
}