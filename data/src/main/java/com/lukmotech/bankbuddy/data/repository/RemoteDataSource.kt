package com.lukmotech.bankbuddy.data.repository

import com.lukmotech.bankbuddy.data.model.TransactionData
import com.lukmotech.bankbuddy.data.model.UserInfoData
import io.reactivex.Observable

interface RemoteDataSource {
    fun getUserInfo(identifier: String): Observable<UserInfoData>

    fun getUserTransaction(userIdentifier: String, limit: Int): Observable<List<TransactionData>>
}