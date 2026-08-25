package com.ramadan.homecare.di

import com.ramadan.homecare.data.repository.AssetRepositoryImpl
import com.ramadan.homecare.data.repository.AttachmentRepositoryImpl
import com.ramadan.homecare.data.repository.MaintenanceRecordRepositoryImpl
import com.ramadan.homecare.domain.repository.AssetRepository
import com.ramadan.homecare.domain.repository.AttachmentRepository
import com.ramadan.homecare.domain.repository.MaintenanceRecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAssetRepository(
        assetRepositoryImpl: AssetRepositoryImpl
    ): AssetRepository

    @Binds
    @Singleton
    abstract fun bindMaintenanceRecordRepository(
        maintenanceRecordRepositoryImpl: MaintenanceRecordRepositoryImpl
    ): MaintenanceRecordRepository

    @Binds
    @Singleton
    abstract fun bindAttachmentRepository(
        attachmentRepositoryImpl: AttachmentRepositoryImpl
    ): AttachmentRepository
}