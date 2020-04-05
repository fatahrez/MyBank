package com.lukmotech.bankbuddy.presentation.mapper

import com.lukmotech.bankbuddy.domain.entities.UserInfoEntity
import com.lukmotech.bankbuddy.presentation.model.UserInfo
import javax.inject.Inject

class UserInfoEntityMapper @Inject constructor(): Mapper<UserInfoEntity, UserInfo> {
    override fun from(e: UserInfo): UserInfoEntity {
        return UserInfoEntity(
            accountBalance = e.accountBalance,
            displayName = e.displayName,
            address = e.address,
            displayableJoinDate = e.displayableJoinDate,
            premiumCustomer = e.premiumCustomer,
            accountNumber = e.accountNumber,
            accountType = e.accountType,
            unbilledTransactionCount = e.unbilledTransactionCount
        )
    }

    override fun to(t: UserInfoEntity): UserInfo {
        return UserInfo(
            accountNumber = t.accountNumber,
            displayName = t.displayName,
            address = t.address,
            displayableJoinDate = t.displayableJoinDate,
            premiumCustomer = t.premiumCustomer,
            accountBalance = t.accountBalance,
            accountType = t.accountType,
            unbilledTransactionCount = t.unbilledTransactionCount,
            isEligibleForUpgrade = t.isEligibleForUpgrade
        )
    }

}