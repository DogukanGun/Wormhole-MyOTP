
package com.dag.check24products.base

import android.annotation.SuppressLint
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dag.check24products.base.appbar.CustomAppbar
import com.dag.check24products.ui.theme.Check24ProductsTheme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BlockSurface(
    appbar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    backgroundColor: Color,
    content: @Composable () -> Unit
) {

    Scaffold(
        topBar = appbar,
        bottomBar = bottomBar,
        backgroundColor = backgroundColor
    ) {
        Surface {
            content()
        }
    }

}

@Composable
fun BlockPreview(content: @Composable () -> Unit){
    Check24ProductsTheme() {
        content()
    }
}

@Preview
@Composable
fun BlockSurfacePreview() {
    BlockSurface(
        appbar = {
            CustomAppbar()
        },
        bottomBar = {
        },
        backgroundColor = Color.LightGray
    ) {
        Text(text = "Deneme")
    }
}