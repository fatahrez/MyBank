package com.lukmotech.bankbuddy.data.mapper

import com.lukmotech.bankbuddy.data.model.TransactionData
import com.lukmotech.bankbuddy.domain.entities.TransactionEntity
import javax.inject.Inject

class TransactionDomainDataMapper @Inject constructor(): Mapper<TransactionEntity, TransactionData> {
    override fun from(e: TransactionData): TransactionEntity {
        return TransactionEntity(
            transactionId = e.transactionId,
            type = e.type,
            amountInCents = e.amountInCents,
            commaSeparatedTags = e.commaSeparatedTags,
            timestamp = e.timestamp,
            flagged = e.flagged,
            remarks = e.remarks
        )
    }

    override fun to(t: TransactionEntity): TransactionData {
        return TransactionData(
            transactionId = t.transactionId,
            type = t.type,
            amountInCents = t.amountInCents,
            commaSeparatedTags = t.commaSeparatedTags,
            timestamp = t.timestamp,
            flagged = t.flagged,
            remarks = t.remarks
        )
    }

}