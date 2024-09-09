package com.dag.check24di.base.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dag.check24di.base.Check24SurfacePreview
import com.dag.check24di.base.navcontroller.NavScreen

@Composable
fun FooterText(
    navController: NavHostController
) {
    TextButton(
        onClick = {
            navController.navigate(NavScreen.WebView.route)
        }
    ) {
        Text(
            text = "C 2021 CHECK24",  // Subtitle
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
@Preview
fun FooterTextPreview(){
    Check24SurfacePreview {
        FooterText(navController = rememberNavController())
    }
}