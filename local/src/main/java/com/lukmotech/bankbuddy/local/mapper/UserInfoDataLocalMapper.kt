package com.lukmotech.bankbuddy.local.mapper

import com.lukmotech.bankbuddy.data.model.UserInfoData
import com.lukmotech.bankbuddy.local.model.UserInfoLocal
import javax.inject.Inject

class UserInfoDataLocalMapper @Inject constructor(): Mapper<UserInfoData, UserInfoLocal>{
    override fun from(e: UserInfoLocal): UserInfoData {
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

    override fun to(t: UserInfoData): UserInfoLocal {
        return UserInfoLocal(
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