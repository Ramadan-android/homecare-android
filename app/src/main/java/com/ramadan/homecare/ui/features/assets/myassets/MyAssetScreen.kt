package com.ramadan.homecare.ui.features.assets.myassets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ramadan.homecare.core.util.AssetCategory
import com.ramadan.homecare.domain.model.Asset
import com.ramadan.homecare.ui.uicomponents.AssetCard
import com.ramadan.homecare.ui.uicomponents.HomeCareFilterChip
import com.ramadan.homecare.ui.uicomponents.TopBar
import java.time.LocalDate

@Composable
fun MyAssetsScreen(
    viewModel: MyAssetsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopBar(
                leadingIcon = Icons.Default.Menu,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "account icon",
                        tint = Color(0xff3E4949),
                        modifier = Modifier
                            .padding(
                                top = 12.dp,
                                bottom = 12.dp,
                                start = 8.dp,
                                end = 16.dp
                            )
                            .clip(RoundedCornerShape(100.dp))
                            .background(color = Color(0xffE7E8E9))
                            .padding(10.dp)
                            .size(20.dp)

                    )
                },
                title = "HomeCare"
            )

        },
        floatingActionButton = {
            FloatingActionButton (
                onClick = viewModel::navigateToAddAsset,
                containerColor = Color(0xff004F52),
                contentColor = Color.White
            ){
                Row(
                    modifier = Modifier
                        .padding(vertical = 18.dp, horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "FAB icon",
                        tint = Color(0xffffffff),
                        modifier = Modifier
                            .size(14.dp)

                    )
                    Text(
                        text = "Add Asset",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xffffffff)

                        )

                    )
                }
            }
        }
    ) {
        MyAssetsContent(
            modifier = Modifier.padding(it),
            state = state,
            onEvent = viewModel::onEvent,
            onNavigateToAssetDetails = viewModel::navigateToAssetDetails

        )
    }
}

@Composable
private fun MyAssetsContent(
    modifier: Modifier = Modifier,
    state: MyAssetsUiState,
    onEvent: (MyAssetsUiEvent) -> Unit,
    onNavigateToAssetDetails: (Long) -> Unit,

    ) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        ) {

        item {
            Text(
                text = "My Assets",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color(0xff191C1D),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                ),
                modifier = Modifier
                    .padding(vertical = 24.dp)
            )
        }
        stickyHeader {
            TextField(
                value = state.query,
                onValueChange = { onEvent(MyAssetsUiEvent.SearchQueryChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(9999.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color(0xffE7E8E9),
                    unfocusedContainerColor = Color(0xffE7E8E9),

                    ),
                placeholder = {
                    Text(
                        text = "Search assets...",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                        ),
                        color = Color(0xff3E4949),

                        )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search text field icon",
                        tint = Color(0xff3E4949),
                        modifier = Modifier
                            .padding(16.dp)
                            .size(18.dp, 24.dp)

                    )
                }

            )
        }

        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                contentPadding = PaddingValues(top = 24.dp, bottom = 16.dp)
                ) {
                items(
                    items = AssetCategory.entries,
                    key = { it }
                ) { category ->
                    HomeCareFilterChip(
                        text = category.name,
                        isSelected = category.name == state.selectedCategory.name,
                        onClick = {
                            onEvent(MyAssetsUiEvent.CategoryChanged(category))
                        },
                        modifier = Modifier.padding(end = 4.dp),
                        contentColor = Color(0xff3E4949),
                        containerColor = Color(0xffE7E8E9),
                        selectedContainerColor = Color(0xffACDAFE),
                        selectedContentColor = Color(0xff30607F),
                    )
                }
            }

        }
        items(
            items = state.myAssets,
            key = { it.assetId }
        ) {
            AssetCard(
                asset = it,
                modifier = Modifier.clickable{
                    onNavigateToAssetDetails(it.assetId)
                }
            )
        }


    }
}



@Composable
@Preview
fun MyAssetsScreenPreview() {
    val state = MyAssetsUiState(
        myAssets = listOf(
            Asset(
                assetId = 1,
                assetName = "LG Washing Machine",
                category = AssetCategory.REFRIGERATOR,
                brand = "brand",
                model = "model",
                serialNumber = "serialNumber",
                purchaseDate = LocalDate.now(),
                hasWarranty = true,
                warrantyExpires = LocalDate.now(),
                trackMaintenance = true,
                intervalMonths = 4,
                notes = "note",
                assetPhoto = "assetPhoto"
            ),
            Asset(
                assetId = 2,
                assetName = "Samsung Refrigerator",
                category = AssetCategory.REFRIGERATOR,
                brand = "brand",
                model = "model",
                serialNumber = "serialNumber",
                purchaseDate = LocalDate.now(),
                hasWarranty = true,
                warrantyExpires = LocalDate.now(),
                trackMaintenance = true,
                intervalMonths = 4,
                notes = "note",
                assetPhoto = "assetPhoto"
            ),
            Asset(
                assetId = 3,
                assetName = "Sony 4K TV",
                category = AssetCategory.REFRIGERATOR,
                brand = "brand",
                model = "model",
                serialNumber = "serialNumber",
                purchaseDate = LocalDate.now(),
                hasWarranty = true,
                warrantyExpires = LocalDate.now(),
                trackMaintenance = true,
                intervalMonths = 4,
                notes = "note",
                assetPhoto = "assetPhoto"
            ),
            Asset(
                assetId = 4,
                assetName = "LG Washing Machine",
                category = AssetCategory.REFRIGERATOR,
                brand = "brand",
                model = "model",
                serialNumber = "serialNumber",
                purchaseDate = LocalDate.now(),
                hasWarranty = true,
                warrantyExpires = LocalDate.now(),
                trackMaintenance = true,
                intervalMonths = 4,
                notes = "note",
                assetPhoto = "assetPhoto"
            )
        )
    )
    Scaffold(
        topBar = {
            TopBar(
                leadingIcon = Icons.Default.Menu,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "account icon",
                        tint = Color(0xff3E4949),
                        modifier = Modifier
                            .padding(
                                top = 12.dp,
                                bottom = 12.dp,
                                start = 8.dp,
                                end = 16.dp
                            )
                            .clip(RoundedCornerShape(100.dp))
                            .background(color = Color(0xffE7E8E9))
                            .padding(10.dp)
                            .size(20.dp)

                    )
                },
                title = "HomeCare"
            )

        }
    ) {
        MyAssetsContent(
            modifier = Modifier.padding(it),
            state = state,
            onEvent = {},
            onNavigateToAssetDetails = {}
        )
    }
}