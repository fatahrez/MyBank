package com.lukmotech.remote.source

import com.lukmotech.bankbuddy.data.model.TransactionData
import com.lukmotech.bankbuddy.data.model.UserInfoData
import com.lukmotech.bankbuddy.data.repository.RemoteDataSource
import com.lukmotech.remote.api.BankingService
import com.lukmotech.remote.mapper.Mapper
import com.lukmotech.remote.model.TransactionNetwork
import com.lukmotech.remote.model.UserInfoNetwork
import io.reactivex.Observable
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val userInfoMapper: Mapper<UserInfoData, UserInfoNetwork>,
    private val transactionMapper: Mapper<TransactionData, TransactionNetwork>,
    private val bankingService: BankingService
): RemoteDataSource {
    override fun getUserInfo(identifier: String): Observable<UserInfoData> {
        return bankingService.getUserInformation(identifier)
            .map { response ->
                userInfoMapper.from(response.userInfo)
            }
    }

    override fun getUserTransaction(
        userIdentifier: String,
        limit: Int
    ): Observable<List<TransactionData>> {
        return bankingService.getUserInformation(userIdentifier)
            .map { response ->
                response.transactions.map { transaction: TransactionNetwork ->
                    transactionMapper.from(transaction)
                }
            }
    }

}