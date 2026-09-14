package com.ramadan.homecare.core.util

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    //    DataBase
    const val DATABASE_NAME = "home_care_db"

    //    Tables
    const val ASSETS_TABLE = "assets"
    const val MAINTENANCE_RECORDS_TABLE = "maintenance_records"
    const val ATTACHMENTS_TABLE = "attachments"

    // DataStore
    const val DATA_STORE_NAME = "home_care_prefs"
    val LANGUAGE_ID = stringPreferencesKey("language_id")
    val THEME_ID = stringPreferencesKey("theme_id")

}