package com.lukmotech.bankbuddy.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionLocal(
    @PrimaryKey @ColumnInfo(name="transaction_id") val transactionId: String,
    @ColumnInfo(name = "transaction_type") val type: String,
    @ColumnInfo(name = "amount_in_cents") val amountInCents: Long,
    @ColumnInfo(name = "comma_separated_tags") val commaSeparatedTags: String,
    @ColumnInfo(name = "transaction_timestamp") val timestamp: Long,
    @ColumnInfo(name = "is_flagged") val flagged: Boolean,
    @ColumnInfo(name = "transaction_remarks") val remarks: String
)