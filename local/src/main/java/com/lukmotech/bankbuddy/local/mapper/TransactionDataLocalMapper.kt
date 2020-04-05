package com.lukmotech.bankbuddy.local.mapper

import com.lukmotech.bankbuddy.data.model.TransactionData
import com.lukmotech.bankbuddy.local.model.TransactionLocal
import javax.inject.Inject

class TransactionDataLocalMapper @Inject constructor(): Mapper<TransactionData, TransactionLocal> {
    override fun from(e: TransactionLocal): TransactionData {
        return TransactionData(
            transactionId = e.transactionId,
            type = e.type,
            amountInCents = e.amountInCents,
            commaSeparatedTags = e.commaSeparatedTags,
            timestamp = e.timestamp,
            flagged = e.flagged,
            remarks = e.remarks
        )
    }

    override fun to(t: TransactionData): TransactionLocal {
        return TransactionLocal(
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