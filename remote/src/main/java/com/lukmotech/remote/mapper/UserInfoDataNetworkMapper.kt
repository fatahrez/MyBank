package com.lukmotech.remote.mapper

import com.lukmotech.bankbuddy.data.model.UserInfoData
import com.lukmotech.remote.model.UserInfoNetwork
import javax.inject.Inject

class UserInfoDataNetworkMapper @Inject constructor(): Mapper<UserInfoData, UserInfoNetwork> {
    override fun from(e: UserInfoNetwork): UserInfoData {
        return UserInfoData(
            accountNumber = e.accountNumber,
            displayName = e.displayName,
            address = e.address,
            displayableJoinDate = e.displayableJoinDate,
            premiumCustomer = e.premiumCustomer,
            accountBalance = e.accountBalance,
            accountType = e.accountType,
            unbilledTransactionCount = e.unbilledTransactionCount
        )
    }

    override fun to(t: UserInfoData): UserInfoNetwork {
        return UserInfoNetwork(
            accountNumber = t.accountNumber,
            displayName = t.displayName,
            address = t.address,
            displayableJoinDate = t.displayableJoinDate,
            premiumCustomer = t.premiumCustomer,
            accountBalance = t.accountBalance,
            accountType = t.accountType,
            unbilledTransactionCount = t.unbilledTransactionCount
        )
    }
}