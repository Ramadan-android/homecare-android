package com.ramadan.homecare.ui.uicomponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CardWrapperItem(
    modifier: Modifier = Modifier,
    cardPadding: PaddingValues = PaddingValues(vertical = 12.dp),
    contentPadding: Dp = 20.dp,
    verticalArrangement:  Arrangement.Vertical = Arrangement.Top,
    hasBorder: Boolean = false,
    containerColor: Color = Color.White,
    content: @Composable () -> Unit
){
    val borderWidth = if (hasBorder)1.dp else 0.dp
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(cardPadding)
            .border(
                width = borderWidth,
                color = Color(0xffE1E3E4),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),

        ) {
        Column(
            modifier = Modifier
                .padding(contentPadding),
            verticalArrangement = verticalArrangement

        ) {
            content()
        }
    }
}