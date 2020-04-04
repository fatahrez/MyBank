package com.lukmotech.bankbuddy.domain.usecases

import com.lukmotech.bankbuddy.domain.entities.TransactionEntity
import com.lukmotech.bankbuddy.domain.qualifiers.Background
import com.lukmotech.bankbuddy.domain.qualifiers.Foreground
import com.lukmotech.bankbuddy.domain.repository.BankingRepository
import com.lukmotech.bankbuddy.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
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