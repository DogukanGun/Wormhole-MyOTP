package com.dag.check24di.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dag.check24di.data.Price
import com.dag.check24di.data.Product
import com.dag.check24di.utils.formatDate
import org.jetbrains.annotations.Async

@Composable
fun CellView(
    product: Product,
    onClick:() -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(3.dp, Color.Gray, RoundedCornerShape(8.dp))  // Gray border

    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            if (product.available) {
                // Image on the left when available
                AsyncImage(
                    model = product.imageURL,
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            // Description section
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = product.description, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Preis: ${product.price.value} ${product.price.currency}", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    repeat(5) { index ->
                        val starColor = if (index < product.rating.toInt()) Color.Yellow else Color.Gray
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = starColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            if (!product.available) {
                Spacer(modifier = Modifier.width(16.dp))

                // Image on the right when not available
                AsyncImage(
                    model = product.imageURL,
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.CenterVertically)
                    )
            }

            if (product.available) {
                Text(
                    text = formatDate(product.releaseDate),
                    modifier = Modifier.align(Alignment.Top)
                )
            }
        }
    }
}

@Preview
@Composable
fun CellViewPreviewForAvailable(){
    CellView(product = Product(
        name = "Name 1",
        description = "Description for item 1",
        price = Price(value = 12.0, currency = "Euro"),
        rating = 4.5,
        releaseDate = 1460629605,
        available = true,
        color ="Yellow",
        colorCode = "FFECB3",
        id = 13412312123,
        imageURL = "https://kredit.check24.de/konto-kredit/ratenkredit/nativeapps/imgs/08.png",
        longDescription = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam\\n\\nLorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam\\n\\nLorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam\\nLorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam",
        type = "Triangle"
    )){}
}

@Preview
@Composable
fun CellViewPreviewForNotAvailable(){
    CellView(product = Product(
        name = "Name 1",
        description = "Description for item 1",
        price = Price(value = 12.0, currency = "Euro"),
        rating = 4.5,
        releaseDate = 1460629605,
        available = false,
        color ="Yellow",
        colorCode = "FFECB3",
        id = 13412312123,
        imageURL = "https://kredit.check24.de/konto-kredit/ratenkredit/nativeapps/imgs/08.png",
        longDescription = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam\\n\\nLorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam\\n\\nLorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam\\nLorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam",
        type = "Triangle"
    )){}
}
