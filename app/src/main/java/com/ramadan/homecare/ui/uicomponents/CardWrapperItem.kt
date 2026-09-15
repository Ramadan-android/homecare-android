package com.ramadan.homecare.ui.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardWrapperItem(
    modifier: Modifier = Modifier,
    cardPadding: PaddingValues = PaddingValues(vertical = 12.dp),
    contentPadding: PaddingValues = PaddingValues(20.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    hasBorder: Boolean = false,
    containerColor: Color = Color.White,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    elevation: CardElevation = CardDefaults.cardElevation(),
    content: @Composable () -> Unit,
){
    val borderWidth = if (hasBorder)1.dp else 0.dp
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(cardPadding)
            .border(
                width = borderWidth,
                color = Color(0xffE1E3E4),
                shape = shape
            ),
        elevation = elevation,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),

        ) {
        Column(
            modifier = Modifier
                .padding(contentPadding),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment

        ) {
            content()
        }
    }
}
@Preview
@Composable
private fun CardWrapperItemPreview(){
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        CardWrapperItem(
            modifier = Modifier.fillMaxWidth(),
            cardPadding = PaddingValues(0.dp),
            contentPadding = PaddingValues(16.dp),
            shape = RoundedCornerShape(0),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
        ) { }
    }

}