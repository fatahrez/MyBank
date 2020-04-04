package com.lukmotech.bankbuddy.domain.repository

import com.lukmotech.bankbuddy.domain.entities.TransactionEntity
import com.lukmotech.bankbuddy.domain.entities.UserInfoEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface BankingRepository {
    fun getUserInfo(identifier: String): Observable<UserInfoEntity>

    fun getUserTransactions(userIdentifier: String, limit: Int): Observable<List<TransactionEntity>>

    fun getFilteredTranscations(
        userIdentifier: String,
        credit: Boolean,
        debit: Boolean,
        flagged: Boolean
    ): Observable<List<TransactionEntity>>

    fun updateTransaction(transaction: TransactionEntity): Completable
}