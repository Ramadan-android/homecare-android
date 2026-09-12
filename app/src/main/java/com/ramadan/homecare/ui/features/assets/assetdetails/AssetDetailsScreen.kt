package com.ramadan.homecare.ui.features.assets.assetdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ramadan.homecare.R
import com.ramadan.homecare.ui.uicomponents.AppAlertDialog
import com.ramadan.homecare.ui.uicomponents.CardWrapperItem
import com.ramadan.homecare.ui.uicomponents.TopBar

@Composable
fun AssetDetailsScreen(
    viewModel: AssetDetailsViewmodel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToEditScreen: (assetId: Long) -> Unit,
    navigateToAddMaintenanceScreen: (assetId: Long) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is AssetDetailsEffectEvents.AddMaintenanceRecord -> navigateToAddMaintenanceScreen(event.assetId)
                AssetDetailsEffectEvents.NavigateBack -> navigateBack()
                is AssetDetailsEffectEvents.NavigateToEditAsset -> navigateToEditScreen(event.assetId)
            }
        }
    }
    Scaffold(
        topBar = {
            TopBar(
                leadingIcon = Icons.Default.ArrowBack,
                onLeadingIconClicked = viewModel::navigateBack,
                title = "Asset Details",
                textAlignCenter = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "edit asset icon",
                        tint = Color(0xff3E4949),
                        modifier = Modifier
                            .padding(end = 23.dp)
                            .clickable(
                                onClick = viewModel::navigateToEditScreen
                            )
                    )

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::navigateToAddMaintenanceScreen,
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
                        contentDescription = "Add Maintenance icon",
                        tint = Color(0xffffffff),
                        modifier = Modifier
                            .size(14.dp)

                    )
                    Text(
                        text = "Add Maintenance",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xffffffff)

                        )

                    )
                }
            }
        }


    ) { innerPadding ->
        AssetDetailsContent(
            modifier = Modifier.padding(innerPadding),
            state = state,
            onEvent = { viewModel.onEvent(it) },
            onClickDelete = viewModel::showDeleteDialogDismiss
        )
        if (state.showDeleteDialog){
            AppAlertDialog(
                title = "Delete ${state.assetName} Asset?",
                message = "Are you sure you want to delete this asset? This action cannot be undone.",
                confirmText = "Delete",
                dismissText = "Cancel",
                onConfirm = {
                    viewModel.onEvent(AssetDetailsEvents.DeleteAsset(state.assetId))
                },
                onDismiss = {
                    viewModel.showDeleteDialogDismiss()
                }
            )
        }
    }
}

@Composable
private fun AssetDetailsContent(
    modifier: Modifier = Modifier,
    state: AssetDetailsUiState,
    onEvent: (AssetDetailsEvents) -> Unit,
    onClickDelete: () -> Unit,

    ) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xffE1E3E4))
            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                AsyncImage(
                    model = state.assetPhoto ?: R.drawable.asset_painter_preview,
                    contentDescription = "asset photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(358.dp, 192.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = state.assetName,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color(0xff191C1D),
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "${state.model}/${state.brand}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    color = Color(0xff3E4949)
                )
                CardWrapperItem(
                    contentPadding = 16.dp,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    hasBorder = true
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ColumnPreviewAssetItem(
                            title = "Category",
                            value = state.category.name,
                            leadingIcon = Icons.Default.Category
                        )
                        state.purchaseDate?.let {
                            ColumnPreviewAssetItem(
                                title = "Purchase Date",
                                value = it.toString(),
                                leadingIcon = null
                            )
                        }

                    }

                    state.serialNumber?.let {
                        ColumnPreviewAssetItem(
                            title = "Serial Number",
                            value = it,
                            leadingIcon = null
                        )
                    }
                }
                ListTile(
                    painter = painterResource(R.drawable.warranty_status_image),
                    title = if (state.warrantyExpires != null)"Warranty Active" else "Warranty Expired",
                    value = state.warrantyExpires.toString(),
                    containerColor = Color(0xffF3F4F5)

                )
                state.nextMaintenanceDate?.let {
                    ListTile(
                        painter = painterResource(R.drawable.next_maintenance_image),
                        title = "Maintenance",
                        value = it.toString(),
                        containerColor = Color(0xffF3F4F5)

                    )
                }
                if (state.maintenanceHistory.isNotEmpty()){
                    Text(
                        text = "History",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = Color(0xff191C1D),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                    state.maintenanceHistory.forEach { record ->
                        ListTile(
                            painter = painterResource(R.drawable.warranty_status_image),
                            title = record.maintenanceType.name,
                            value = record.date.toString(),
                            containerColor = Color.White,
                            hasBorder = true

                        )


                    }
                }
                if (state.attachments.isNotEmpty()){
                    Text(
                        text = "Attachments",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = Color(0xff191C1D),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                    state.attachments.forEach { attachment ->

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            ListTile(
                                painter = painterResource(R.drawable.warranty_status_image),
                                title = attachment.fileName,
                                value = attachment.fileSize.toString(),
                                containerColor = Color.White,
                                hasBorder = true
                            )
                        }


                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 80.dp)
                        .clickable(
                            onClick = onClickDelete
                        ),
                    horizontalArrangement = Arrangement.Center,

                    ) {
                    Image(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "delete asset icon",
                        modifier = Modifier.size(16.dp, 18.dp),
                        colorFilter = ColorFilter.tint(Color(0xffBA1A1A))
                    )
                    Text(
                        text = "Delete Asset",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400
                        ),
                        color = Color(0xffBA1A1A),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }

}



@Composable
fun ListTile(
    modifier: Modifier = Modifier,
    painter: Painter,
    title: String,
    value: String,
    trailingText: String? = null,
    contentPadding: Dp = 16.dp,
    containerColor: Color = Color(0xffF3F4F5),
    hasBorder: Boolean = false
) {
    CardWrapperItem(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        containerColor = containerColor,
        hasBorder = hasBorder

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier.size(32.dp, 36.dp)

            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color(0xff191C1D)
                )
                Text(
                    text = value,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    color = Color(0xff3E4949)
                )
            }
            trailingText?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    color = Color(0xff191C1D)

                )
            }
        }

    }
}

@Composable
fun ColumnPreviewAssetItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    leadingIcon: ImageVector?,
) {

    Column(modifier = modifier) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            ),
            color = Color(0xff3E4949)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)

        ) {
            leadingIcon?.let {
                Image(
                    imageVector = it,
                    contentDescription = "${it.name} icon",
                    modifier = Modifier.size(12.dp, 15.dp)
                )
            }

            Text(
                text = value,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400
                ),
                color = Color(0xff191C1D)
            )

        }
    }

}

@Preview
@Composable
private fun AssetDetailsPreview() {

    Scaffold(
        topBar = {
            TopBar(
                leadingIcon = Icons.Default.ArrowBack,
                title = "Asset Details",
                textAlignCenter = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "edit asset icon",
                        tint = Color(0xff3E4949),
                        modifier = Modifier
                            .padding(end = 23.dp)
                            .clickable {
                            }
                    )

                }
            )
        }
    ) { innerPadding ->
        AssetDetailsContent(
            modifier = Modifier.padding(innerPadding),
            state = AssetDetailsUiState(),
            onEvent = {},
            onClickDelete = {}

        )
    }
}