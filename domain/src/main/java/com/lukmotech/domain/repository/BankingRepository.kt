package com.lukmotech.domain.repository

import com.lukmotech.domain.entities.TransactionEntity
import com.lukmotech.domain.entities.UserInfoEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

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