package com.lukmotech.bankbuddy.domain.utils

import com.lukmotech.bankbuddy.domain.entities.TransactionEntity
import com.lukmotech.bankbuddy.domain.entities.UserInfoEntity


class TestDataGenerator {

    companion object {
        fun generateUserInfo(): UserInfoEntity {
            return UserInfoEntity(
                "1BFC9A38E6C7",
                "Abdul Fatah",
                "39, Komarock, Eastland, Nairobi - 00100",
                "March-12, 2020",
                false,
                4579.75,
                "savings",
                4
            )
        }

        fun generateUpgradableUser(): UserInfoEntity {
            return UserInfoEntity(
                "1BFC9A38E6C7",
                "Agent Smith",
                "39, Komarock, Eastland, Nairobi - 00100",
                "March-01, 2020",
                false,
                45579.75,
                "credit-card",
                11
            )
        }

        fun generateTransactions(): List<TransactionEntity> {
            val t1 = TransactionEntity (
                "B2C148D3-F48A-6757-FADF-1BFC9A38E6C7",
                "debit",
                42125,
                "retail, manual, debit-card",
                1541007660,
                false,
                ""
            )
            val t2 = TransactionEntity(
                "A1D959FF-51A9-8C8F-EC86-016AE83B0033",
                "credit",
                155780,
                "transfer, credit-card, online, adjusted, auto",
                1551162246,
                true,
                "inward remittance by Globalmantics LLC"
            )
            val t3 = TransactionEntity(
                "839F206D-6CE4-9C7E-073A-64E8EB190222",
                "debit",
                42076,
                "credit-card, retail",
                1551591517,
                false,
                "purchase at XYZ retail store"
            )
            return listOf(t1, t2, t3)
        }
    }
}