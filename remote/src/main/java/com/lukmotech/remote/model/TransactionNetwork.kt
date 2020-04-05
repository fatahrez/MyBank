package com.lukmotech.remote.model

import com.google.gson.annotations.SerializedName

data class TransactionNetwork(
    @SerializedName("id") val transactionId: String,
    @SerializedName("type") val type: String,
    @SerializedName("amount-in-cents") val amountInCents: Long,
    @SerializedName("tags") val commaSeparatedTags: String,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("flagged") val flagged: Boolean,
    @SerializedName("remark") val remarks: String
)