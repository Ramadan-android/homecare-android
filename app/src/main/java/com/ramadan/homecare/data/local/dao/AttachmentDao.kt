package com.ramadan.homecare.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.ramadan.homecare.core.util.Constants
import com.ramadan.homecare.data.local.entity.AttachmentEntity

@Dao
interface AttachmentDao {

    @Insert
    suspend fun insertAttachment(attachment: AttachmentEntity)

    @Query(
        """
    SELECT * FROM ${Constants.ATTACHMENTS_TABLE}
    WHERE assetId = :assetId
    AND maintenanceId = :maintenanceId
"""
    )
    suspend fun getAttachmentsByAssetIdAndMaintenanceId(
        assetId: Long,
        maintenanceId: Long
    ): List<AttachmentEntity>

    @Query("""
    SELECT * FROM ${Constants.ATTACHMENTS_TABLE}
    WHERE assetId = :assetId
    AND maintenanceId IS NULL
""")
    suspend fun getAssetAttachments(assetId: Long): List<AttachmentEntity>

    @Query("DELETE FROM ${Constants.ATTACHMENTS_TABLE} WHERE id = :attachmentId")
    suspend fun deleteAttachmentById(attachmentId: Long)
}
