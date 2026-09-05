package com.ramadan.homecare.ui.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramadan.homecare.R

@Composable
fun RowSwitcher(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    isChecked: Boolean,
    onClick: () -> Unit,
    iconColor: Color = Color(0xff00696D),
){
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Icon(
            imageVector = icon,
            contentDescription = "${icon.name} icon",
            tint = iconColor,
            modifier = Modifier.size(22.dp)
                .padding(end = 3.5.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.W400,
                color = Color(0xff191C1D)
            ),
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = isChecked,
            onCheckedChange = { onClick() },
            modifier = Modifier
                .size(48.dp, 24.dp),
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color(0xff6F7979),
                uncheckedTrackColor = Color(0xffE1E3E4),
                checkedTrackColor = Color(0xff004F52),
                checkedThumbColor = Color.White,
                checkedBorderColor = Color.Transparent,
                uncheckedBorderColor = Color.Transparent,

            )
        )

    }
}

@Composable
@Preview
private fun RowSwitcherPreview(){
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
            .background(Color(0xffF8F9FA)),
        verticalArrangement = Arrangement.Center
    ) {
        RowSwitcher(
            icon = ImageVector.vectorResource(id = R.drawable.warranty_icon),
            text = "Warranty",
            isChecked = true,
            onClick = {}
        )
        RowSwitcher(
                icon = ImageVector.vectorResource(id = R.drawable.warranty_icon),
        text = "Warranty",
        isChecked = false,
        onClick = {}
        )
    }
}