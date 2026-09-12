package com.ramadan.homecare.domain.usecase.assetdetails

import com.ramadan.homecare.domain.model.Attachment
import com.ramadan.homecare.domain.repository.AttachmentRepository
import javax.inject.Inject

class GetAttachmentUseCase @Inject constructor(
    private val attachmentRepository: AttachmentRepository,

    ) {
    suspend operator fun invoke(assetId: Long): List<Attachment> =
        attachmentRepository.getAllAssetAttachments(assetId)
}