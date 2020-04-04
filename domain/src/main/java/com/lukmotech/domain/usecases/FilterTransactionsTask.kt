package com.lukmotech.domain.usecases

import com.lukmotech.domain.entities.TransactionEntity
import com.lukmotech.domain.qualifiers.Background
import com.lukmotech.domain.qualifiers.Foreground
import com.lukmotech.domain.repository.BankingRepository
import com.lukmotech.domain.usecases.base.ObservableUseCase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class FilterTransactionsTask @Inject constructor(
    private val bankingRepository: BankingRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
): ObservableUseCase<List<TransactionEntity>, FilterTransactionsTask.Params>(
    backgroundScheduler,
    foregroundScheduler
) {
    override fun generateObservable(input: Params?): Observable<List<TransactionEntity>> {
        if (input == null) {
            throw IllegalArgumentException("FilterTransactionsTask parameter can't be null")
        }
        return bankingRepository.getFilteredTranscations(
            userIdentifier = input.userIdentifier,
            credit = input.credit,
            debit = input.debit,
            flagged = input.flagged
        )
    }

    data class Params (
        val userIdentifier: String,
        val credit: Boolean,
        val debit: Boolean,
        val flagged: Boolean
    )
}