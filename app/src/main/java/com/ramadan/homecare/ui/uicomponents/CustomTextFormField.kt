package com.ramadan.homecare.ui.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextFormField(
    modifier: Modifier = Modifier,
    fieldTitle: String,
    titleFontSize: TextUnit = 11.sp,
    titleFontWeight: FontWeight = FontWeight.W400,
    titleColor: Color = Color(0xff3E4949),
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    placeholder: String,
    trillingIcon: ImageVector? = null,
    leadingIcon: ImageVector? = null,
    onClickTrillingIcon: (() -> Unit)? = null,
    onClickleadingIcon: (() -> Unit)? = null,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    bottomPadding: Dp = 16.dp,
    errorMassage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text

) {
    Column(
        modifier = modifier
            .fillMaxWidth()

            .padding(bottom = bottomPadding)
    ) {
        Text(
            text = fieldTitle,
            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp, end = 4.dp),
            style = TextStyle(
                fontSize = titleFontSize,
                fontWeight = titleFontWeight,
            ),
            color = titleColor,
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            readOnly = readOnly,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xffBEC9C9),
                    shape = RoundedCornerShape(8.dp)
                ),
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                color = Color(0xff191C1D)
            ),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorTextColor = Color(0xffFF0000),
                errorIndicatorColor = Color(0xffFF0000),
                errorContainerColor = Color.Transparent,
                cursorColor = Color(0xff3E4949),
                errorSupportingTextColor = Color(0xffFF0000)
                ),
            singleLine = singleLine,
            maxLines = maxLines,
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                    ),
                    color = Color(0xffBEC9C9),

                    )
            },
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = "${leadingIcon.name} icon",
                        tint = Color(0xff3E4949),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(18.dp, 24.dp)
                            .clickable(
                                enabled = onClickleadingIcon != null
                            ) {
                                onClickleadingIcon?.invoke()
                            }

                    )
                }
            },
            trailingIcon = trillingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = "${trillingIcon.name} icon",
                        tint = Color(0xff3E4949),
                        modifier = Modifier
                            .padding(16.dp)
                            .size(18.dp, 24.dp)
                            .clickable(
                                enabled = onClickTrillingIcon != null
                            ) {
                                onClickTrillingIcon?.invoke()
                            }

                    )
                }
            },
            isError = errorMassage != null,
            supportingText = errorMassage?.let{
                {
                    Text(
                        text = errorMassage,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W300,
                        ),
                        color = Color(0xffFF0000)
                    )
                }
            }


        )

    }
}

@Composable
@Preview
private fun CustomTextFormFieldPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center

    ) {
        CustomTextFormField(
            fieldTitle = "fieldTitle",
            value = "value",
            onValueChange = {},
            placeholder = "placeholder",
            bottomPadding = 0.dp,
        )
        CustomTextFormField(
            fieldTitle = "fieldTitle",
            value = "value",
            onValueChange = {},
            placeholder = "placeholder",
        )

    }
}