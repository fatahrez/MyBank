package com.lukmotech.domain.entities

data class UserInfoEntity(
    val accountNumber: String,
    val displayName: String,
    val address: String,
    val displayableJoinDate: String,
    val premiumCustomer: Boolean,
    val accountBalance: Double,
    val accountType: String,
    val unbilledTransactionCount: Int
) {
    companion object {
        private const val ACCOUNT_UPGRADE_BALANCE = 30000
    }

    val isEligibleForUpgrade: Boolean
        get() = accountBalance >= ACCOUNT_UPGRADE_BALANCE
}