package com.lukmotech.bankbuddy.local.database

import androidx.room.*
import com.lukmotech.bankbuddy.local.model.TransactionLocal
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY transaction_timestamp DESC LIMIT :limit")
    fun getUserTransactions(limit: Int): Observable<List<TransactionLocal>>

    @Update
    fun updateTransaction(transaction: TransactionLocal): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTransactions(transactions: List<TransactionLocal>)

    @Query("SELECT * FROM transactions WHERE transaction_id = :transactionId")
    fun getTransactionById(transactionId: String): Observable<TransactionLocal>

    @Query("DELETE FROM transactions")
    fun clearedCachedTransactions(): Completable
}