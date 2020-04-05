package com.lukmotech.bankbuddy.presentation.utils

import com.lukmotech.bankbuddy.presentation.model.Transaction
import com.lukmotech.bankbuddy.presentation.model.UserInfo

class TestDataGenerator {

    companion object {
        fun generateUserInfo(): UserInfo {
            return UserInfo(
                "1BFC9A38E6C7",
                "John Doe",
                "307, Palm drive, Virdigris Square, CA - 95014",
                "March-12, 2018",
                false,
                4579.75,
                "savings",
                4,
                false
            )
        }

        fun generateUpgradableUserInfo(): UserInfo {
            return UserInfo(
                "1BFC9A38E6C7",
                "Agent Smith",
                "307, Palm drive, Virdigris Square, CA - 95014",
                "March-01, 2018",
                false,
                45579.75,
                "credit-card",
                11,
                true
            )
        }

        fun generateTransactions(): List<Transaction> {
            val t1 = Transaction(
                "B2C148D3-F48A-6757-FADF-1BFC9A38E6C7",
                "debit",
                42125,
                "retail, manual, debit-card",
                1541007660,
                false,
                "",
                false
            )
            val t2 = Transaction(
                "A1D959FF-51A9-8C8F-EC86-016AE83B0033",
                "credit",
                155780,
                "transfer, credit-card, online, adjusted, auto",
                1551162246,
                true,
                "inward remittance by Globalmantics LLC",
                false
            )
            val t3 = Transaction(
                "839F206D-6CE4-9C7E-073A-64E8EB190222",
                "debit",
                420760,
                "credit-card, retail",
                1551591517,
                false,
                "purchase at XYZ retail store",
                true
            )

            return listOf(t1, t2, t3)
        }
    }
}