package com.dag.check24products.base.appbar

import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.dag.check24products.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dag.check24products.base.BlockPreview


@Composable
fun CustomAppbar() {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h3
                )
            }
        },
        navigationIcon = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {

            }
        },
        backgroundColor = MaterialTheme.colors.background,
        actions = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {

            }
        }
    )
}

@Composable
@Preview
fun CustomAppbarPreview() {
    BlockPreview {
        CustomAppbar()
    }
}