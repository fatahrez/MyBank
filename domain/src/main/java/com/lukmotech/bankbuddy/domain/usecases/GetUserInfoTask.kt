package com.lukmotech.bankbuddy.domain.usecases

import com.lukmotech.bankbuddy.domain.entities.UserInfoEntity
import com.lukmotech.bankbuddy.domain.qualifiers.Background
import com.lukmotech.bankbuddy.domain.qualifiers.Foreground
import com.lukmotech.bankbuddy.domain.repository.BankingRepository
import com.lukmotech.bankbuddy.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetUserInfoTask @Inject constructor(
    private val bankingRepository: BankingRepository,
    @Background val backgroundScheduler: Scheduler,
    @Foreground val foregroundScheduler: Scheduler
): ObservableUseCase<UserInfoEntity, String>(backgroundScheduler, foregroundScheduler) {
    override fun generateObservable(input: String?): Observable<UserInfoEntity> {
        if (input ==  null) {
            throw IllegalArgumentException("User identifier can't be null")
        }
        return bankingRepository.getUserInfo(input)
    }
}