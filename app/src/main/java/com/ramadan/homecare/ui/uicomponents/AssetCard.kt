package com.ramadan.homecare.ui.uicomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramadan.homecare.R
import com.ramadan.homecare.core.util.AssetCategory
import com.ramadan.homecare.domain.model.Asset
import java.time.LocalDate

@Composable
fun AssetCard(
    modifier: Modifier = Modifier,
    asset: Asset
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffF3F4F5)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xffE7E8E9)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.asset_painter_preview),
                contentDescription = "asset icon",
                modifier = Modifier
                    .size(64.dp)
            )
            Column{
                Text(
                    text = asset.assetName,
                    modifier = Modifier
                        .padding(bottom = 4.dp),
                    color = Color(0xff191C1D),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,

                    )

                )
                Text(
                    text = asset.category.name,
                    modifier = Modifier
                        .padding(bottom = 4.dp),
                    color = Color(0xff191C1D),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,

                    )

                )
            }
        }
    }
}

@Composable
@Preview
private fun AssetCardPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        AssetCard(
            asset = Asset(
                assetId = 1,
                assetName = "Samsung Refrigerator",
                category = AssetCategory.REFRIGERATOR,
                brand = "brand",
                model = "model",
                serialNumber = "serialNumber",
                purchaseDate = LocalDate.now(),
                hasWarranty = true,
                warrantyExpires = LocalDate.now(),
                trackMaintenance = true,
                intervalMonths = 6,
                notes = "notes",
                assetPhoto = "assetPhoto"
            )
        )
    }
}