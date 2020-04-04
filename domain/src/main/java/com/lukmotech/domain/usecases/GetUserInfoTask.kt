package com.lukmotech.domain.usecases

import com.lukmotech.domain.entities.UserInfoEntity
import com.lukmotech.domain.qualifiers.Background
import com.lukmotech.domain.qualifiers.Foreground
import com.lukmotech.domain.repository.BankingRepository
import com.lukmotech.domain.usecases.base.ObservableUseCase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
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