package com.lukmotech.bankbuddy.domain.usecases

import com.lukmotech.bankbuddy.domain.entities.TransactionEntity
import com.lukmotech.bankbuddy.domain.qualifiers.Background
import com.lukmotech.bankbuddy.domain.qualifiers.Foreground
import com.lukmotech.bankbuddy.domain.repository.BankingRepository
import com.lukmotech.bankbuddy.domain.usecases.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
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