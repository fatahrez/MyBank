package com.lukmotech.bankbuddy.local.source

import com.lukmotech.bankbuddy.data.model.TransactionData
import com.lukmotech.bankbuddy.data.model.UserInfoData
import com.lukmotech.bankbuddy.data.repository.LocalDataSource
import com.lukmotech.bankbuddy.local.database.TransactionDao
import com.lukmotech.bankbuddy.local.database.UserInfoDao
import com.lukmotech.bankbuddy.local.mapper.TransactionDataLocalMapper
import com.lukmotech.bankbuddy.local.mapper.UserInfoDataLocalMapper
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val userInfoMapper: UserInfoDataLocalMapper,
    private val transactionMapper: TransactionDataLocalMapper,
    private val userInfoDAO: UserInfoDao,
    private val transactionDAO: TransactionDao
): LocalDataSource {

    companion object {
        private const val DEFAULT_LIMIT = 40
        private const val TYPE_CREDIT = "credit"
        private const val TYPE_DEBIT = "debit"
    }

    override fun getUserInfo(identifier: String): Observable<UserInfoData> {
        return userInfoDAO.getUserInfo(identifier)
            .map {
                userInfoMapper.from(it)
            }
    }

    override fun saveUserInfo(userInfoData: UserInfoData) {
        userInfoDAO.addUserInfo(
            userInfoMapper.to(userInfoData)
        )
    }

    override fun getUserTransactions(
        userIdentifier: String,
        limit: Int
    ): Observable<List<TransactionData>> {
        return transactionDAO.getUserTransactions(limit)
            .map {localTransactions ->
                localTransactions.map {
                    transactionMapper.from(it)
                }
            }
    }

    override fun saveUserTransactions(userIdentifier: String, transactions: List<TransactionData>) {
        transactionDAO.addTransactions(
            transactions.map {
                transactionMapper.to(it)
            }
        )
    }

    override fun getTransaction(transactionId: String): Observable<TransactionData> {
        return transactionDAO.getTransactionById(transactionId)
            .map {
                transactionMapper.from(it)
            }
    }

    override fun getFilteredTransactions(
        userIdentifier: String,
        credit: Boolean?,
        debit: Boolean?,
        flagged: Boolean?
    ): Observable<List<TransactionData>> {
        return transactionDAO.getUserTransactions(DEFAULT_LIMIT)
            .map { localTransactions ->
                localTransactions
                    .filter { if (flagged == null || !flagged) true else (it.flagged == flagged) }
                    .filter { if (credit == null || !credit) true else (it.type == TYPE_CREDIT) }
                    .filter {if (debit == null || !debit) true else (it.type == TYPE_DEBIT)}
            }.map { filteredTransactions ->
                filteredTransactions.map {
                    transactionMapper.from(it)
                }
            }
    }

    override fun updateTransaction(transaction: TransactionData): Completable {
        return transactionDAO.updateTransaction(
            transactionMapper.to(transaction)
        )
    }
}