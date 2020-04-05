package com.lukmotech.bankbuddy.presentation.viewmodels

import androidx.lifecycle.*
import com.lukmotech.bankbuddy.domain.entities.TransactionEntity
import com.lukmotech.bankbuddy.domain.usecases.FilterTransactionsTask
import com.lukmotech.bankbuddy.domain.usecases.GetUserTransactionsTask
import com.lukmotech.bankbuddy.domain.usecases.TransactionStatusUpdaterTask
import com.lukmotech.bankbuddy.presentation.mapper.Mapper
import com.lukmotech.bankbuddy.presentation.model.Resource
import com.lukmotech.bankbuddy.presentation.model.Status
import com.lukmotech.bankbuddy.presentation.model.Transaction
import com.lukmotech.bankbuddy.presentation.qualifier.UserIdentity
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import javax.inject.Inject

class TransactionVM @Inject internal constructor(
    @UserIdentity userIdentifier: String,
    private val transactionMapper: Mapper<TransactionEntity, Transaction>,
    getUserTransactionsTask: GetUserTransactionsTask,
    private val filterTransactionsTask: FilterTransactionsTask,
    private val transactionStatusUpdaterTask: TransactionStatusUpdaterTask
): ViewModel() {
    private val filterRequestLiveData = MutableLiveData<FilterTransactionsTask.Params>()

    private var filterRequest = FilterTransactionsTask.Params(
        userIdentifier = userIdentifier,
        credit = true,
        debit = true,
        flagged = true
    )

    private val disposables = CompositeDisposable()

    private val transactionMediator = MediatorLiveData<Resource<List<Transaction>>>()

    val transactionListSource: LiveData<Resource<List<Transaction>>>
        get() = transactionMediator

    private val transactionResource = getUserTransactionsTask
        .buildUseCase(GetUserTransactionsTask.Params(userIdentifier, 40))
        .map { transactionEntities ->
            transactionEntities.map {
                transactionMapper.to(it)
            }
        }.map { Resource.success(it) }
        .startWith(Resource.loading())
        .onErrorResumeNext(Function {
            Observable.just(Resource.error(it.localizedMessage))
        }).toFlowable(BackpressureStrategy.LATEST)
        .toLiveData()

    private val filteredTransactions = Transformations.switchMap(filterRequestLiveData) {input ->
        filterTransactionsTask
            .buildUseCase(input)
            .map { transactionEntities ->
                transactionEntities.map {
                    transactionMapper.to(it)
                }
            }.map { Resource.success(it) }
            .startWith(Resource.loading())
            .onErrorResumeNext(
                Function {
                    Observable.just(Resource.error(it.localizedMessage))
                }
            ).toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }

    init {
        transactionMediator.addSource(filteredTransactions) {
            transactionMediator.value = it
        }

        transactionMediator.addSource(transactionResource) {
            transactionMediator.value = it
            if (it.status != Status.LOADING) {
                transactionMediator.removeSource(transactionResource)
                resetFilterOptions()
            }
        }

    }

    fun filterTransaction(
        credit: Boolean? = true,
        debit: Boolean? = true,
        flagged: Boolean?= true
    ) {
        filterRequest = filterRequest.copy(
            credit = credit?: filterRequest.credit,
            debit = debit?: filterRequest.debit,
            flagged = flagged?: filterRequest.flagged
        )
        filterRequestLiveData.postValue(filterRequest)
    }

    fun toggleFlaggedStatus(transaction: Transaction) {
        val transactionNew = transactionMapper.from(
            transaction.copy(
                flagged = !transaction.flagged
            )
        )
        disposables.add(
            transactionStatusUpdaterTask
                .buildUseCase(transactionNew)
                .onErrorComplete()
                .subscribe()
        )
    }

    fun resetFilters() {
        transactionMediator.removeSource(transactionResource)
        transactionMediator.addSource(transactionResource) {
            transactionMediator.value = it
            if (it.status != Status.LOADING) {
                transactionMediator.removeSource(transactionResource)
                resetFilterOptions()
            }
        }
    }
    private fun resetFilterOptions() {
        filterRequest = filterRequest.copy(credit = false, debit = false, flagged = false)
        filterRequestLiveData.postValue(filterRequest)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}

