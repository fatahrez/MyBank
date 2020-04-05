package com.lukmotech.remote.mapper

import com.lukmotech.bankbuddy.data.model.TransactionData
import com.lukmotech.remote.model.TransactionNetwork
import javax.inject.Inject

class TransactionDataNetworkMapper @Inject constructor(): Mapper<TransactionData, TransactionNetwork> {
    override fun from(e: TransactionNetwork): TransactionData {
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

    override fun to(t: TransactionData): TransactionNetwork {
        return TransactionNetwork(
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