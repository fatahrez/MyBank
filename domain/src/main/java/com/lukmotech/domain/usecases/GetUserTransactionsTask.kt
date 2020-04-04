package com.lukmotech.domain.usecases

import com.lukmotech.domain.entities.TransactionEntity
import com.lukmotech.domain.qualifiers.Background
import com.lukmotech.domain.qualifiers.Foreground
import com.lukmotech.domain.repository.BankingRepository
import com.lukmotech.domain.usecases.base.ObservableUseCase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class GetUserTransactionsTask @Inject constructor(
    private val bankingRepository: BankingRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
): ObservableUseCase<List<TransactionEntity>, GetUserTransactionsTask.Params>(
    backgroundScheduler,
    foregroundScheduler
) {
    override fun generateObservable(input: Params?): Observable<List<TransactionEntity>> {
        if (input == null) {
            throw IllegalArgumentException("GetUserTransactionsTask Params can't be null")
        }
        return bankingRepository.getUserTransactions(input.identifier, input.limit)
    }

    data class Params (val identifier: String, val limit: Int)
}