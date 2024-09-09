package com.dag.check24di.base

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dag.check24di.ui.theme.Check24DITheme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Check24Surface(
    appbar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    content: @Composable (innerPadding:PaddingValues) -> Unit
) {

    Scaffold(
        topBar = appbar,
        bottomBar = bottomBar,
    ) { innerPadding->
        Surface(modifier = Modifier.padding(innerPadding)) {
            content(innerPadding)
        }
    }

}

@Composable
fun Check24SurfacePreview(content: @Composable () -> Unit){
    Check24DITheme() {
        content()
    }
}
