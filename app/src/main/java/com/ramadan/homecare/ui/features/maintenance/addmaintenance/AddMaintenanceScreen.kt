package com.ramadan.homecare.ui.features.maintenance.addmaintenance

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramadan.homecare.core.util.MaintenanceType
import com.ramadan.homecare.ui.uicomponents.CardWrapperItem
import com.ramadan.homecare.ui.uicomponents.CustomTextFormField
import com.ramadan.homecare.ui.uicomponents.DatePickerDialog
import com.ramadan.homecare.ui.uicomponents.TopBar
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun AddMaintenanceScreen(
    viewModel: AddMaintenanceViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                AddMaintenanceUiEffectEvent.NavigateBack -> navigateBack()
            }
        }
    }
    Scaffold(
        topBar = {
            TopBar(
                title = "Add Maintenance",
                leadingIcon = Icons.Default.ArrowBack,
                onLeadingIconClicked = navigateBack,
                containerColor = Color(0xffF8F9FA)
            )
        },
        bottomBar = {
            CardWrapperItem(
                modifier = Modifier.fillMaxWidth(),
                cardPadding = PaddingValues(0.dp),
                contentPadding = PaddingValues(16.dp),
                shape = 0.dp,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )

            ) {
                Button(
                    onClick = { viewModel.onEvent(AddMaintenanceUiEvent.SaveMaintenanceRecordClicked) },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff004F52)
                    ),
                    enabled = state.buttonEnabled


                ) {
                    if (state.isLoading){
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = Color.White,
                            strokeWidth = 2.dp

                        )
                    }else
                        Icon(
                            imageVector = Icons.Outlined.Save,
                            contentDescription = "save maintenance",
                            modifier = Modifier.size(18.dp),
                            tint = Color.White
                        )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Save Maintenance",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )

                    )
                }
            }
        }
    ) {
        AddMaintenanceContent(
            modifier = Modifier.padding(it),
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddMaintenanceContent(
    modifier: Modifier = Modifier,
    state: AddMaintenanceUiState,
    onEvent: (AddMaintenanceUiEvent) -> Unit
){
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedDate = Instant
                    .ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                return !selectedDate.isAfter(LocalDate.now())
            }

        }
    )
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ){
        onEvent(AddMaintenanceUiEvent.AddAttachmentClicked(it.map { it.toString() }))
    }
    LazyColumn(
        modifier = modifier.padding(vertical = 24.dp, horizontal = 16.dp),

    ){
        item {

            ExposedDropdownMenuBox(
                expanded = state.maintenanceTypeMenuVisible,
                onExpandedChange = {
                    onEvent(AddMaintenanceUiEvent.MaintenanceTypeMenuIconClicked) },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CustomTextFormField(
                    modifier = Modifier.menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable
                    ),
                    fieldTitle = "Maintenance Type",
                    value = if(state.maintenanceType == MaintenanceType.All) "Select Type" else state.maintenanceType.name,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = "Select a maintenance type",
                    trillingIcon = Icons.Default.ArrowDropDown,
                    errorMassage = state.maintenanceTypeMenuErrorMessage,
                )
                ExposedDropdownMenu(
                    expanded = state.maintenanceTypeMenuVisible,
                    onDismissRequest = {
                        onEvent(AddMaintenanceUiEvent.MaintenanceTypeMenuIconClicked) }
                ) {
                    MaintenanceType.entries.forEach { maintenanceType ->
                        if (maintenanceType == MaintenanceType.All) return@forEach
                        DropdownMenuItem(
                            text = { Text(maintenanceType.name) },
                            onClick = {
                                onEvent(AddMaintenanceUiEvent.MaintenanceTypeChanged(maintenanceType))
                                onEvent(AddMaintenanceUiEvent.MaintenanceTypeMenuIconClicked)                                 }
                        )
                    }

                }
            }
            CustomTextFormField(
                fieldTitle = "Date",
                value = state.date?.toString() ?: "mm/dd/yyyy",
                onValueChange = {},
                readOnly = true,
                placeholder = "mm/dd/yyyy",
                trillingIcon = Icons.Outlined.DateRange,
                onClickTrillingIcon = { onEvent(AddMaintenanceUiEvent.ToggleDatePicker) },
            )
            if (state.showDatePicker) {
                DatePickerDialog(
                    onClickDismiss = { onEvent(AddMaintenanceUiEvent.ToggleDatePicker) },
                    onClickSet = {
                        val selectedDateMillis = datePickerState.selectedDateMillis ?: return@DatePickerDialog
                        val date = Instant.ofEpochMilli(selectedDateMillis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()

                        onEvent(AddMaintenanceUiEvent.DateChanged(date))
                        onEvent(AddMaintenanceUiEvent.ToggleDatePicker)
                    },
                    state = datePickerState
                )
            }
            CustomTextFormField(
                fieldTitle = "Cost",
                value = state.cost ?: "",
                keyboardType = KeyboardType.NumberPassword,
                onValueChange = {onEvent(AddMaintenanceUiEvent.CostChanged(it))},
                leadingIcon = Icons.Default.AttachMoney,
                placeholder = "0.00",
                errorMassage = state.costErrorMessage,
            )
           CustomTextFormField(
               fieldTitle = "Service Provider",
               value = state.serviceProvider ?:"",
               onValueChange = {onEvent(AddMaintenanceUiEvent.ServiceProviderChanged(it))},
               placeholder = "e.g. Bob's Plumbing",

            )
            CustomTextFormField(
                fieldTitle = "Notes",
                titleFontSize = 22.sp,
                titleColor = Color(0xff004F52),
                value = state.notes ?:"",
                onValueChange = {onEvent(AddMaintenanceUiEvent.NotesChanged(it))},
                placeholder = "Add any additional details or findings...\n\n\n",
                singleLine = false,
                maxLines = 5,
            )

            CardWrapperItem(
                modifier = Modifier.fillMaxWidth(),
                cardPadding = PaddingValues(0.dp),
                contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp),
                shape = 12.dp,
                containerColor = Color(0xffEDEEEF)

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Column {
                        Text(
                            text = "${state.attachments.size} Attachment",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xff191C1D)
                            )
                        )
                        Text(
                            text = "Receipts, invoices, or photos",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400,
                                color = Color(0xff3E4949)
                            )
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.FilePresent,
                        contentDescription = "add attachment icon",
                        tint = Color(0xff004F52),
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color(0xffE1E3E4))
                            .clickable(onClick = {
                                filePickerLauncher.launch(
                                    arrayOf(
                                        "application/pdf",
                                        "application/msword",
                                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                                        "image/*"
                                    )
                                )

                            })
                            .padding(10.dp)
                            .size(20.dp)


                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AddMaintenancePreview(){
    Scaffold(
        topBar = {
            TopBar(
                title = "Add Maintenance",
                leadingIcon = Icons.Default.ArrowBack,
                containerColor = Color(0xffF8F9FA)
            )
        },
        bottomBar = {
            CardWrapperItem(
                modifier = Modifier.fillMaxWidth(),
                cardPadding = PaddingValues(0.dp),
                contentPadding = PaddingValues(16.dp),
                shape = 0.dp,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )

            ) {
                Button(
                    onClick = {  },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff004F52)
                    )

                ) {
                    Icon(
                        imageVector = Icons.Outlined.Save,
                        contentDescription = "save maintenance",
                        modifier = Modifier.size(18.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Save Maintenance",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )

                    )
                }
            }
        }
    ){
        AddMaintenanceContent(
            modifier = Modifier.padding(it),
            state = AddMaintenanceUiState(),
            onEvent = {}
        )
    }

}