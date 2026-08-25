package com.ramadan.homecare.data.local.typeConverters

import androidx.room3.ColumnTypeConverter
import java.time.LocalDate

class LocalDateConverter {

    //    LocalDate
    @ColumnTypeConverter
    fun localDateToLong(localDate: LocalDate): Long = localDate.toEpochDay()

    @ColumnTypeConverter
    fun longToLocalDate(epochDay: Long): LocalDate = LocalDate.ofEpochDay(epochDay)
}