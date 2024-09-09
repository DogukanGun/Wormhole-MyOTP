package com.dag.check24di.feature.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.dag.check24di.base.Check24SurfacePreview
import com.dag.check24di.base.common.Check24Button
import com.dag.check24di.base.common.FooterText
import com.dag.check24di.data.Product
import com.dag.check24di.utils.formatDate
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductView(
    productId:Long,
    productVM: ProductVM = koinViewModel(),
    navController: NavHostController
) {
    val state by productVM.viewState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = productId) {
        productVM.getProduct(productId)
        productVM.isProductFavorite(productId)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        state.product?.let { product ->
            item {
                ProductViewInner(
                    product = product,
                    buttonText = state.buttonText,
                    onClick = {
                        productVM.changeFavoriteStatus(productId)
                    }
                )
            }
        }
        item {
            FooterText(navController = navController)
        }
    }
}

@Composable
fun ProductViewInner(
    product: Product,
    onClick:()->Unit,
    buttonText:String
){
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        AsyncImage(
            model = product.imageURL,
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(16.dp))


        // Name and Price section
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Preis: ${product.price.value} ${product.price.currency}",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                repeat(5) { index ->
                    val starColor =
                        if (index < product.rating.toInt()) Color.Yellow else Color.Gray
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = starColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        Text(
            text = formatDate(product.releaseDate),
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 4.dp)
        )

    }
    Text(
        text = product.description,
        modifier = Modifier
            .padding(16.dp)
    )
    Check24Button(onClick = onClick, text = buttonText, modifier = Modifier.padding(16.dp))
    Text(
        text = "Beschreibung",
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(16.dp)
    )
    Text(
        text = product.longDescription,
        modifier = Modifier
            .padding(16.dp)
    )
}

@Composable
@Preview
fun ProductPreview() {
    Check24SurfacePreview {
        ProductView(
            navController = rememberNavController(),
            productId = 13412312123
        )
    }
}