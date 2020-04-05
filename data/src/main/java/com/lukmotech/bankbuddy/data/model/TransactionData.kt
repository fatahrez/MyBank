package com.lukmotech.bankbuddy.data.model

data class TransactionData(
    val transactionId: String,
    val type: String,
    val amountInCents: Long,
    val commaSeparatedTags: String,
    val timestamp: Long,
    val flagged: Boolean,
    val remarks: String
)