package com.ramadan.homecare.ui.features.assets.addasset

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.ramadan.homecare.R
import com.ramadan.homecare.core.util.AssetCategory
import com.ramadan.homecare.ui.uicomponents.CardWrapperItem
import com.ramadan.homecare.ui.uicomponents.CustomTextFormField
import com.ramadan.homecare.ui.uicomponents.DatePickerDialog
import com.ramadan.homecare.ui.uicomponents.RowSwitcher
import com.ramadan.homecare.ui.uicomponents.TopBar
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun AddAssetScreen(
    viewModel: AddAssetViewModel = hiltViewModel(),
    navigateToMyAssets: () -> Unit,
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                AddAssetUiEffectEvent.NavigateBack -> navigateBack()
                AddAssetUiEffectEvent.NavigateToMyAssets -> navigateToMyAssets()
            }
        }
    }
    Scaffold(
        topBar = {
            TopBar(
                title = "${state.screenMode.name} Asset",
                leadingIcon = Icons.Default.ArrowBack,
                onLeadingIconClicked = viewModel::navigateBack,
                containerColor = Color(0xffF8F9FA)
            )
        },
        containerColor = Color(0xffF8F9FA)
    ) { paddingValues ->
        AddAssetContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = viewModel::onEvent,
            navigateToMyAssets = viewModel::navigateToMyAssets,
            navigateBack = viewModel::navigateBack
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddAssetContent(
    modifier: Modifier = Modifier,
    state: AddAssetUiState,
    onEvent: (AddAssetUiEvent) -> Unit,
    navigateToMyAssets: () -> Unit,
    navigateBack: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            @SuppressLint("SuspiciousIndentation")
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedDate = Instant
                    .ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                return !selectedDate.isAfter(LocalDate.now())
            }
        }
    )
    val warrantyDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedDate = Instant
                    .ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                return !selectedDate.isBefore(LocalDate.now())
            }
        }
    )
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            onEvent(AddAssetUiEvent.PickPhoto(it.toString()))
        }
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            CardWrapperItem(
                cardPadding = PaddingValues(top = 8.dp, bottom = 12.dp),
            ) {
                Text(
                    text = "Basic Information",
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xff004F52)
                    )
                )
                CustomTextFormField(
                    fieldTitle = "Asset Name",
                    value = state.assetName,
                    onValueChange = { onEvent(AddAssetUiEvent.AssetNameChanged(it)) },
                    placeholder = "e.g., HVAC System",
                    errorMassage = state.assetNameError,

                    )
                ExposedDropdownMenuBox(
                    expanded = state.isVisibleCategoryMenu,
                    onExpandedChange = {
                        onEvent(AddAssetUiEvent.CategoryMenuIconClicked(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    CustomTextFormField(
                        modifier = Modifier.menuAnchor(
                            type = ExposedDropdownMenuAnchorType.PrimaryNotEditable
                        ),
                        fieldTitle = "Category",
                        value = if (state.category == AssetCategory.All) "Select Category" else state.category.name,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = "Select a category",
                        trillingIcon = Icons.Default.ArrowDropDown,
                        bottomPadding = 0.dp,
                        errorMassage = state.assetCategoryError,
                    )
                    ExposedDropdownMenu(
                        expanded = state.isVisibleCategoryMenu,
                        onDismissRequest = {
                            onEvent(AddAssetUiEvent.CategoryMenuIconClicked(false))
                        }
                    ) {
                        AssetCategory.entries.forEach { category ->
                            if (category == AssetCategory.All) return@forEach
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    onEvent(AddAssetUiEvent.AssetCategoryChanged(category))
                                    onEvent(AddAssetUiEvent.CategoryMenuIconClicked(false))
                                }
                            )
                        }

                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CustomTextFormField(
                        modifier = Modifier.weight(1f),
                        fieldTitle = "Brand",
                        value = state.brand,
                        onValueChange = { onEvent(AddAssetUiEvent.BrandChanged(it)) },
                        placeholder = "e.g., Carrier",
                        errorMassage = state.brandError,

                        )
                    CustomTextFormField(
                        modifier = Modifier.weight(1f),
                        fieldTitle = "Model",
                        value = state.model,
                        onValueChange = { onEvent(AddAssetUiEvent.ModelChanged(it)) },
                        placeholder = "Model No.",
                        errorMassage = state.modelError,

                        )
                }
                CustomTextFormField(
                    fieldTitle = "Serial Number",
                    value = state.serialNumber,
                    onValueChange = { onEvent(AddAssetUiEvent.SerialNumberChanged(it)) },
                    placeholder = "Serial No.",

                    )
                CustomTextFormField(
                    fieldTitle = "Purchase Date",
                    value = state.purchaseDate?.toString() ?: "mm/dd/yyyy",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = "mm/dd/yyyy",
                    trillingIcon = Icons.Outlined.DateRange,
                    onClickTrillingIcon = { onEvent(AddAssetUiEvent.DatePickerIconClicked) },
                    bottomPadding = 0.dp,
                )
                if (state.isVisibleDatePicker) {
                    DatePickerDialog(
                        onClickDismiss = { onEvent(AddAssetUiEvent.DatePickerIconClicked) },
                        onClickSet = {
                            val date = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()

                            onEvent(AddAssetUiEvent.PickDate(DateType.PURCHASE_DATE, date))
                            onEvent(AddAssetUiEvent.DatePickerIconClicked)
                        },
                        state = datePickerState
                    )
                }
            }
            CardWrapperItem {
                RowSwitcher(
                    icon = ImageVector.vectorResource(id = R.drawable.warranty_icon),
                    text = "Warranty",
                    isChecked = state.hasWarranty,
                    onClick = { onEvent(AddAssetUiEvent.WarrantyChanged) }
                )
                if (state.hasWarranty)
                    CustomTextFormField(
                        modifier = Modifier.padding(top = 16.dp),
                        fieldTitle = "Warranty Expires",
                        value = state.warrantyExpires?.toString() ?: "mm/dd/yyyy",
                        onValueChange = {},
                        readOnly = true,
                        placeholder = "mm/dd/yyyy",
                        trillingIcon = Icons.Outlined.DateRange,
                        onClickTrillingIcon = { onEvent(AddAssetUiEvent.WarrantyDatePickerIconClicked) },
                        bottomPadding = 0.dp,
                        errorMassage = state.warrantyExpiresError,
                    )
                if (state.isVisibleWarrantyDatePicker) {
                    DatePickerDialog(
                        onClickDismiss = { onEvent(AddAssetUiEvent.WarrantyDatePickerIconClicked) },
                        onClickSet = {
                            val date =
                                Instant.ofEpochMilli(warrantyDatePickerState.selectedDateMillis!!)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()

                            onEvent(AddAssetUiEvent.PickDate(DateType.WARRANTY_EXPIRES, date))
                            onEvent(AddAssetUiEvent.WarrantyDatePickerIconClicked)
                        },
                        state = warrantyDatePickerState
                    )
                }
            }
            CardWrapperItem {
                RowSwitcher(
                    icon = ImageVector.vectorResource(id = R.drawable.track_maintenance_icon),
                    text = "Track Maintenance",
                    isChecked = state.trackMaintenance,
                    onClick = { onEvent(AddAssetUiEvent.TrackMaintenanceChanged) }
                )
                if (state.trackMaintenance) {
                    ExposedDropdownMenuBox(
                        expanded = state.isVisibleMonthsMenu,
                        onExpandedChange = {
                            onEvent(AddAssetUiEvent.MonthsMenuIconClicked(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        CustomTextFormField(
                            modifier = Modifier.menuAnchor(
                                type = ExposedDropdownMenuAnchorType.PrimaryNotEditable
                            ),
                            fieldTitle = "Interval (Months)",
                            value = if (state.intervalMonths == null) "Select Interval" else "${state.intervalMonths} Months",
                            onValueChange = {},
                            readOnly = true,
                            placeholder = "Select interval",
                            trillingIcon = Icons.Default.ArrowDropDown,
                            onClickTrillingIcon = { onEvent(AddAssetUiEvent.MonthsMenuIconClicked(!state.isVisibleMonthsMenu)) },
                            bottomPadding = 0.dp,
                            errorMassage = state.intervalMonthsError,
                        )
                        ExposedDropdownMenu(
                            expanded = state.isVisibleMonthsMenu,
                            onDismissRequest = {
                                onEvent(AddAssetUiEvent.MonthsMenuIconClicked(false))
                            }
                        ) {
                            listOf(1, 3, 6, 12).forEach { month ->
                                DropdownMenuItem(
                                    text = { Text("$month Months") },
                                    onClick = {
                                        onEvent(AddAssetUiEvent.IntervalMonthsChanged(month))
                                        onEvent(AddAssetUiEvent.MonthsMenuIconClicked(false))
                                    }
                                )
                            }

                        }

                    }
                    Text(
                        text = "Recommended for optimal performance.",
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W400,
                            color = Color(0xff004F52)
                        )

                    )
                }


            }
            CardWrapperItem {
                CustomTextFormField(
                    fieldTitle = "Notes",
                    titleFontSize = 22.sp,
                    titleColor = Color(0xff004F52),
                    value = state.notes,
                    onValueChange = { onEvent(AddAssetUiEvent.NotesChanged(it)) },
                    placeholder = "Add any additional details, contractor \n" +
                            "information, or quirks about this asset...\n\n",
                    singleLine = false,
                    maxLines = 5,
                )
            }
            AsyncImage(
                model = state.assetPhoto ?: R.drawable.add_photo_image,
                contentDescription = "upload photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 34.dp)
                    .size(358.dp, 172.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {

                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )

                    },
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 40.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Cancel",
                    modifier = Modifier.clickable(onClick = navigateBack),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xff004F52)
                    )
                )
                Button(
                    onClick = { onEvent(AddAssetUiEvent.SaveAsset) },
                    enabled = !state.isLoading,
                    modifier = Modifier.padding(start = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff004F52)
                    )

                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator()
                    } else
                        Text(
                            text = "Save Asset",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400,
                                color = Color.White
                            )

                        )
                }
            }
        }

    }
}


@Composable
@Preview
private fun AddAssetPreview() {
    Scaffold(
        topBar = {
            TopBar(
                title = "Add Asset",
                leadingIcon = Icons.Default.ArrowBack,
                titleColor = Color(0xff191C1D),
                containerColor = Color(0xffF8F9FA)
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 40.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Cancel",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xff004F52)
                    )
                )
                Button(
                    onClick = { },
                    modifier = Modifier.padding(start = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff004F52)
                    )

                ) {
                    Text(
                        text = "Save Asset",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.White
                        )

                    )
                }
            }
        },

        containerColor = Color(0xffF8F9FA)

    ) { paddingValues ->
        AddAssetContent(
            modifier = Modifier.padding(paddingValues),
            state = AddAssetUiState(),
            onEvent = {},
            navigateToMyAssets = {},
            navigateBack = {}
        )

    }
}