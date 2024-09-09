package com.dag.check24di.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.dag.check24di.R
import com.dag.check24di.base.common.Check24Button

@Composable
fun ErrorCell(
    paddingValues: PaddingValues,
    onReload: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(horizontal = 8.dp)
            .border(3.dp, Color.Gray, RoundedCornerShape(8.dp))  // Gray border

    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Error icon
            Image(
                painter = painterResource(id = R.drawable.baseline_error),  // Replace with your image name
                contentDescription = "Error Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(128.dp)
                    .align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Probleme",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Die Daten konnten nicht geladen werden.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Check24Button(onClick = onReload, text = "Neuladen")
            }
        }

    }
}

@Preview
@Composable
fun ErrorCellPreview() {
    ErrorCell(paddingValues = PaddingValues()) {}
}
