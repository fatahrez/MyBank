package com.lukmotech.bankbuddy.data.utils

import com.lukmotech.bankbuddy.data.model.TransactionData
import com.lukmotech.bankbuddy.data.model.UserInfoData

class TestDataGenerator {
    companion object {
        fun generateUserInfo(): UserInfoData {
            return UserInfoData(
                "1BFC9A38E6C7",
                "John Doe",
                "307, Palm drive, Virdigris Square, CA - 95014",
                "March-12, 2018",
                false,
                4579.75,
                "savings",
                4
            )
        }

        fun generateTransactions(): List<TransactionData> {
            val t1 = TransactionData(
                "B2C148D3-F48A-6757-FADF-1BFC9A38E6C7",
                "debit",
                42125,
                "retail, manual, debit-card",
                1541007660,
                false,
                ""
            )
            val t2 = TransactionData(
                "A1D959FF-51A9-8C8F-EC86-016AE83B0033",
                "credit",
                155780,
                "transfer, credit-card, online, adjusted, auto",
                1551162246,
                true,
                "inward remittance by Globalmantics LLC"
            )
            val t3 = TransactionData(
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