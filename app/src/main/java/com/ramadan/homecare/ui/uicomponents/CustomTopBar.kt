package com.ramadan.homecare.ui.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    onLeadingIconClicked: (() -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    title: String = "",
    titleColor: Color = Color(0xff004F52),
    containerColor: Color = Color(0xffF3F4F5)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(containerColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.let {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "${leadingIcon.name} icon",
                tint = Color(0xff3E4949),
                modifier = Modifier.padding(
                    top = 18.dp,
                    bottom = 18.dp,
                    start = 16.dp,
                    end = 8.dp
                )
                    .clickable(enabled = onLeadingIconClicked != null){
                        onLeadingIconClicked?.invoke()
                    }
            )
        }

        Text(
            text = title,
            modifier = Modifier
                .padding(
                    vertical = 18.dp,
                    horizontal = 8.dp
                )
                .weight(1f),
            style = MaterialTheme.typography.titleLarge.copy(
                color = titleColor,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        trailingIcon?.let {
            it()
        }
    }
}