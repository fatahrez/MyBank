package com.lukmotech.domain.usecases.base

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler

abstract class CompletableUseCase<in Input> constructor(
    private val backgroundScheduler: Scheduler,
    private val foregroundScheduler: Scheduler
) {
    protected abstract fun generateCompletable(input: Input? = null): Completable

    fun buildUseCase(input: Input?=null): Completable {
        return generateCompletable(input)
            .subscribeOn(backgroundScheduler)
            .observeOn(foregroundScheduler)
    }
}