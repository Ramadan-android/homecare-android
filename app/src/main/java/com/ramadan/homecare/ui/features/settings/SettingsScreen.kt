package com.ramadan.homecare.ui.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramadan.homecare.ui.uicomponents.CardWrapperItem
import com.ramadan.homecare.ui.uicomponents.TopBar
import com.ramadan.homecare.BuildConfig


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsRouteScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {viewModel.onEvent(SettingsUiEvent.ToggleShowBottomSheet)},
            containerColor = Color.White

        ) {
            Text(
                text = "HomeCare App is currently in beta.",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xff191C1D)
                ),
                modifier = Modifier.padding(16.dp)
            )
        }


    }
    Scaffold(
        topBar = {
            TopBar(
                title = "Settings",
                leadingIcon = Icons.Default.ArrowBack,
                onLeadingIconClicked = navigateBack,
                containerColor = Color(0xffF8F9FA)
            )
        },

    ) {

        SettingsRouteContent(
            state = state,
            modifier = Modifier.padding(it),
            onEvent = viewModel::onEvent,
            )

    }
}

@Composable
private fun SettingsRouteContent(
    state: SettingsUiState,
    modifier: Modifier = Modifier,
    onEvent: (SettingsUiEvent) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = "APPEARANCE",
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xff004F52)
                ),
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )
            CardWrapperItem(
                cardPadding = PaddingValues(0.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                SettingColumnCard(
                    cardTitle = "Theme",
                    cardSubtitle = "Choose your preferred visual style",
                    selectedItem = state.theme.name,
                    itemList = Theme.entries.map { it.toString() },
                    onClickItem = { onEvent(SettingsUiEvent.ThemeChanged(Theme.valueOf(it))) }
                )
                SettingColumnCard(
                    cardTitle = "Language",
                    cardSubtitle = "Choose your preferred language",
                    selectedItem = state.language.name,
                    itemList = Language.entries.map { it.toString() },
                    onClickItem = { onEvent(SettingsUiEvent.LanguageChanged(Language.valueOf(it))) }
                )

            }
            Text(
                text = "ABOUT",
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xff004F52)
                ),
                modifier = Modifier.padding(start = 4.dp, top = 32.dp, bottom = 8.dp)
            )

            CardWrapperItem(
                cardPadding = PaddingValues(0.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),

                ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = "App Version",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xff191C1D)
                        ),
                    )
                    Text(
                        text = BuildConfig.VERSION_NAME,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                            color = Color(0xff3E4949)
                        ),

                    )

                }
            }
            CardWrapperItem(
                modifier = Modifier
                    .clickable(
                        onClick = { onEvent(SettingsUiEvent.ToggleShowBottomSheet) }
                    ),
                cardPadding = PaddingValues(0.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),

                ) {
                Text(
                    text = "About HomeCare",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xff191C1D)
                    ),

                    )

            }
        }
    }
}

@Composable
private fun SettingColumnCard(
    modifier: Modifier = Modifier,
    itemList: List<String>,
    selectedItem: String = itemList[0],
    cardTitle: String,
    cardSubtitle: String,
    onClickItem: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = cardTitle,
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 28.sp,
                color = Color(0xff191C1D)
            ),
        )
        Text(
            text = cardSubtitle,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 20.sp,
                color = Color(0xff3E4949)
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        CardWrapperItem(
            cardPadding = PaddingValues(0.dp),
            contentPadding = PaddingValues(4.dp),
            containerColor = Color(0xffE1E3E4),
            shape = RoundedCornerShape(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemList.forEach {
                    val textBackgroundColor =
                        if (selectedItem == it) Color.White else Color.Transparent
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 20.sp,
                            color = if (it == selectedItem) Color(0xff191C1D) else Color(0xff3E4949)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(textBackgroundColor)
                            .padding(28.dp, 8.dp)
                            .clickable(onClick = { onClickItem(it) })
                    )

                }
            }
        }


    }
}

@Preview
@Composable
private fun SettingsRoutePreview() {
    Scaffold(
        topBar = {
            TopBar(
                title = "Add Maintenance",
                leadingIcon = Icons.Default.ArrowBack,
                onLeadingIconClicked = {},
                containerColor = Color(0xffF8F9FA)
            )
        },

        ) {
        SettingsRouteContent(
            modifier = Modifier.padding(it),
            state = SettingsUiState(),
            onEvent = {},
        )

    }
}