package com.dag.check24di.base

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dag.myapplication.composebase.appbar.CustomAppbar
import com.dag.myapplication.ui.theme.BlockMobileTheme


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
    BlockMobileTheme() {
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