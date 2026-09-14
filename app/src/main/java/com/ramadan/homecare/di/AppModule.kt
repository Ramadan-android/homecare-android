package com.ramadan.homecare.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room3.Room
import com.ramadan.homecare.core.util.Constants
import com.ramadan.homecare.data.local.dao.AssetDao
import com.ramadan.homecare.data.local.dao.AttachmentDao
import com.ramadan.homecare.data.local.dao.MaintenanceRecordDao
import com.ramadan.homecare.data.local.database.HomeCareDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {
        @Provides
        @Singleton
        fun provideHomeCareDatabase(@ApplicationContext context: Context): HomeCareDatabase{
            return Room.databaseBuilder(
                context = context,
                klass = HomeCareDatabase::class.java,
                name = Constants.DATABASE_NAME
            ).build()
        }

        @Provides
        @Singleton
        fun provideAssetDao(db: HomeCareDatabase): AssetDao = db.assetDao()

        @Provides
        @Singleton
        fun provideMaintenanceRecordDao(db: HomeCareDatabase): MaintenanceRecordDao = db.maintenanceRecordDao()

        @Provides
        @Singleton
        fun provideAttachmentDao(db: HomeCareDatabase): AttachmentDao = db.attachmentDao()

        @Provides
        @Singleton
        fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = {
                    context.preferencesDataStoreFile(Constants.DATA_STORE_NAME)
                }
            )
        }
    }
}