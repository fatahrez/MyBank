package com.lukmotech.bankbuddy.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfoLocal(
    @PrimaryKey @ColumnInfo(name = "account_id") val accountNumber: String,
    @ColumnInfo(name = "display_name") val displayName: String,
    @ColumnInfo(name = "user_address") val address: String,
    @ColumnInfo(name = "join_date") val displayableJoinDate: String,
    @ColumnInfo(name = "is_premium_customer") val premiumCustomer: Boolean,
    @ColumnInfo(name = "account_balance") val accountBalance: Double,
    @ColumnInfo(name = "type") val accountType: String,
    @ColumnInfo(name = "number_of_unbilled_transactions") val unbilledTransactionCount: Int
)