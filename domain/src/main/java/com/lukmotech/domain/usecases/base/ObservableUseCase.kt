package com.lukmotech.domain.usecases.base

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler

abstract class ObservableUseCase<T, in Input> constructor(
    private val backgroundScheduler: Scheduler,
    private val foregroundScheduler: Scheduler
){
    protected abstract fun generateObservable(input: Input? = null): Observable<T>

    fun buildUseCase(input: Input? = null): Observable<T> {
        return generateObservable(input)
            .subscribeOn(backgroundScheduler)
            .observeOn(foregroundScheduler)
    }
}