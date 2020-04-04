package com.lukmotech.domain.usecases

import com.lukmotech.domain.entities.TransactionEntity
import com.lukmotech.domain.qualifiers.Background
import com.lukmotech.domain.qualifiers.Foreground
import com.lukmotech.domain.repository.BankingRepository
import com.lukmotech.domain.usecases.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class TransactionStatusUpdaterTask @Inject constructor(
    private val bankingRepository: BankingRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
): CompletableUseCase<TransactionEntity>(
    backgroundScheduler,
    foregroundScheduler
) {
    override fun generateCompletable(input: TransactionEntity?): Completable {
        if (input == null) {
            throw IllegalArgumentException("TransactionStatusUpdaterTask parameter can't be null")
        }
        return bankingRepository.updateTransaction(input)
    }
}