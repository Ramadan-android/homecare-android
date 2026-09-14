package com.ramadan.homecare.domain.usecase.asset

import com.ramadan.homecare.domain.model.Attachment
import com.ramadan.homecare.domain.repository.AttachmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAttachmentUseCase @Inject constructor(
    private val attachmentRepository: AttachmentRepository,

    ) {
    operator fun invoke(assetId: Long): Flow<List<Attachment>> =
        attachmentRepository.getAllAssetAttachments(assetId)
}