package com.lukmotech.remote.model

import com.google.gson.annotations.SerializedName

data class UserInfoNetwork(
    @SerializedName("account-number") val accountNumber: String,
    @SerializedName("account-holder-name") val displayName: String,
    @SerializedName("registered-address") val address: String,
    @SerializedName("join-date") val displayableJoinDate: String,
    @SerializedName("premium") val premiumCustomer: Boolean,
    @SerializedName("balance") val accountBalance: Double,
    @SerializedName("account-type") val accountType: String,
    @SerializedName("unbilled-transactions") val unbilledTransactionCount: Int
)