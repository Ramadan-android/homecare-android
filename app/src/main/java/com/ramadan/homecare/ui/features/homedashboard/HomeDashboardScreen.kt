package com.ramadan.homecare.ui.features.homedashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ramadan.homecare.R
import com.ramadan.homecare.ui.features.assets.assetdetails.ListTile
import com.ramadan.homecare.ui.uicomponents.CardWrapperItem
import com.ramadan.homecare.ui.uicomponents.TopBar

@Composable
fun HomeDashboardScreen(
    viewModel: HomeDashboardViewModel = hiltViewModel(),
    navigateToAddAsset: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                HomeDashboardUiEffectEvent.AddAsset -> navigateToAddAsset()
            }
        }
    }
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
            if (state.totalAssets.isBlank()) return@Scaffold
            FloatingActionButton(
                onClick = viewModel::navigateToAddAsset,
                containerColor = Color(0xff004F52),
                contentColor = Color.White
            ) {
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
        HomeDashboardContent(
            modifier = Modifier.padding(it),
            state = state,
            navigateToAddAsset = viewModel::navigateToAddAsset
        )
    }
}

@Composable
private fun HomeDashboardContent(
    modifier: Modifier = Modifier,
    state: HomeDashboardUiState,
    navigateToAddAsset: () -> Unit
) {

    if (state.totalAssets.isBlank()) {
        HomeDashboardEmpty(modifier,navigateToAddAsset)
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp),
        ) {
            item {
                Text(
                    text = "Good morning",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xff191C1D),
                        lineHeight = 32.sp
                    ),

                    )
                Text(
                    text = "Here's what needs your attention.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xff3E4949),
                        lineHeight = 20.sp

                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),

                ) {
                    CardWrapperItem(
                        modifier = Modifier.weight(1f),
                        cardPadding = PaddingValues(end = 0.dp),
                        contentPadding = PaddingValues(16.dp),
                        containerColor = Color(0xff00696D),
                    ) {
                        AsyncImage(
                            model = R.drawable.total_asset,
                            contentDescription = "",
                            modifier = Modifier
                                .size(40.dp)

                        )
                        Text(
                            text = state.totalAssets,
                            modifier = Modifier.padding(top = 24.dp, bottom = 4.dp),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xff96E5EA),
                                lineHeight = 32.sp
                            )
                        )
                        Text(
                            text = "Total Assets",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xff96E5EA),
                                lineHeight = 20.sp
                            )
                        )

                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CardWrapperItem(
                            cardPadding = PaddingValues(end = 0.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
                            containerColor = Color(0xffFFDAD6),
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically

                            ) {
                                Column() {
                                    Text(
                                        text = state.maintenanceDue,
                                        style = TextStyle(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xff93000A),
                                            lineHeight = 32.sp
                                        )
                                    )
                                    Text(
                                        text = "Maintenance Due",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xff93000A),
                                            lineHeight = 20.sp
                                        )
                                    )
                                }
                                AsyncImage(
                                    model = R.drawable.maintenance_due,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(40.dp)

                                )

                            }


                        }
                        CardWrapperItem(
                            cardPadding = PaddingValues(end = 0.dp),
                            contentPadding = PaddingValues(16.dp),
                            containerColor = Color(0xffE7E8E9),
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(50.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column() {
                                    Text(
                                        text = state.expiringWarranties,
                                        style = TextStyle(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xff3E4949),
                                            lineHeight = 32.sp
                                        )
                                    )
                                    Text(
                                        text = "Expiring\n" +
                                                "Warranties",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xff3E4949),
                                            lineHeight = 20.sp
                                        )
                                    )
                                }
                                AsyncImage(
                                    model = R.drawable.expiring_warranties,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(bottom = 24.dp)

                                )

                            }


                        }
                    }
                }
                Text(
                    text = "Upcoming Maintenance",
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xff191C1D),
                        lineHeight = 28.sp
                    )
                )
            }
            items(
                items = state.upcomingMaintenance,
                key = { it.assetId }
            ) {
                ListTile(
                    painter = rememberVectorPainter(Icons.Default.Menu),
                    title = it.assetName,
                    value = it.date.toString(),
                    containerColor = Color(0xffF3F4F5)
                )
            }
            item {
                Text(
                    text = "Recent Activity",
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xff191C1D),
                        lineHeight = 28.sp

                    )
                )
                CardWrapperItem(
                    cardPadding = PaddingValues(bottom = 32.dp),
                    contentPadding = PaddingValues(16.dp),
                    containerColor = Color(0xffF3F4F5),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            VerticalDivider(
                                thickness = 2.dp,
                                color = Color(0xffE7E8E9),
                                modifier = Modifier.height(12.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .size(16.dp)
                                    .background(color = Color(0xffE1E3E4))
                                    .padding(1.dp)
                                    .clip(RoundedCornerShape(50.dp))

                                    .background(color = Color(0xff004F52))

                            )
                            VerticalDivider(
                                thickness = 2.dp,
                                color = Color(0xffE7E8E9),
                                modifier = Modifier.height(36.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .size(12.dp)
                                    .background(color = Color(0xffE1E3E4))

                            )
                            VerticalDivider(
                                thickness = 2.dp,
                                color = Color(0xffE7E8E9),
                                modifier = Modifier.height(24.dp)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text = state.recentActivity.assetName + " added",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xff191C1D),
                                    lineHeight = 20.sp
                                )
                            )
                            Text(
                                text = state.recentActivity.purchaseDate.toString(),
                                modifier = Modifier.padding(top = 4.dp, bottom = 28.dp),
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xff3E4949),
                                    lineHeight = 16.sp
                                )
                            )
                            Text(
                                text = state.recentActivity.maintenanceType,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xff191C1D),
                                    lineHeight = 20.sp
                                )
                            )
                            Text(
                                text = if (state.recentActivity.maintenanceDate != null) state.recentActivity.maintenanceDate.toString() else "",
                                modifier = Modifier.padding(top = 4.dp),
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xff3E4949),
                                    lineHeight = 16.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeDashboardEmpty(
    modifier: Modifier = Modifier,
    navigateToAddAsset: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
            .padding(16.dp),

    ) {
        item {
            Text(
                text = "Good morning",
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xff191C1D),
                    lineHeight = 32.sp
                ),
            )
            CardWrapperItem(
                cardPadding = PaddingValues(0.dp),
                contentPadding = PaddingValues(vertical = 25.dp, horizontal = 42.dp),
                hasBorder = true,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Image(
                    painter = painterResource(R.drawable.empty_dashboard),
                    contentDescription = "empty dashboard",
                    modifier = Modifier.fillMaxWidth()
                        .padding(24.dp),
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    text = "Start your home inventory",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xff191C1D),
                        lineHeight = 28.sp
                    )
                )
                Text(
                    text = "Add your first asset to start tracking\n" +
                            "       warranties and maintenance.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xff3E4949),
                        lineHeight = 20.sp
                    )
                )
                Button(
                    onClick = navigateToAddAsset,
                    modifier = Modifier.padding(top = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff004F52),
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 24.dp),
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
                            text = "Add Your First Asset",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xffffffff)

                            )

                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun HomeDashboardPreview() {
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
            FloatingActionButton(
                onClick = {},
                containerColor = Color(0xff004F52),
                contentColor = Color.White
            ) {
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
        HomeDashboardContent(
            modifier = Modifier.padding(it),
            state = HomeDashboardUiState(),
            navigateToAddAsset = {}
        )
    }
}

