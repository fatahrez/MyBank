package com.lukmotech.bankbuddy.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukmotech.bankbuddy.domain.repository.BankingRepository
import com.lukmotech.bankbuddy.domain.usecases.FilterTransactionsTask
import com.lukmotech.bankbuddy.domain.usecases.GetUserTransactionsTask
import com.lukmotech.bankbuddy.domain.usecases.TransactionStatusUpdaterTask
import com.lukmotech.bankbuddy.presentation.mapper.TransactionEntityMapper
import com.lukmotech.bankbuddy.presentation.model.Status
import com.lukmotech.bankbuddy.presentation.utils.TestDataGenerator
import com.lukmotech.bankbuddy.presentation.viewmodels.TransactionVM
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class TransactionVMTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: BankingRepository

    private lateinit var transactionVM: TransactionVM
    private val transactionMapper= TransactionEntityMapper()

    private val transactions = TestDataGenerator.generateTransactions()
    private val transactionEntities = transactions.map { transactionMapper.from(it) }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(repository.getUserTransactions(anyString(), anyInt()))
            .thenReturn(Observable.just(transactionEntities))

        val getUserTransactionsTask = getTransactions()
        val filteredTransactionsTask = getFilteredTransactions()
        val statusUpdaterTask = getStatusUpdaterTask()

        transactionVM = TransactionVM(
            "123",
            transactionMapper,
            getUserTransactionsTask,
            filteredTransactionsTask,
            statusUpdaterTask
        )
    }


    @Test
    fun test_getUserTransactions_success() {
        Mockito.`when`(
            repository.getFilteredTransactions(
                anyString(),
                anyBoolean(),
                anyBoolean(),
                anyBoolean()
            )
        ).thenReturn(Observable.just(transactionEntities))

        val transactionListSource = transactionVM.transactionListSource

        transactionListSource.observeForever {  }

        assertTrue(
            transactionListSource.value?.status == Status.SUCCESS
                    && transactionListSource.value?.data == transactions
        )
    }

    @Test
    fun test_getUserTransactions_error() {
        val errorMsg = "fetch transaction error"
        Mockito.`when`(
            repository.getFilteredTransactions(
                anyString(),
                anyBoolean(),
                anyBoolean(),
                anyBoolean()
            )
        ).thenReturn(Observable.error(Throwable(errorMsg)))

        val transactionListSource = transactionVM.transactionListSource

        transactionListSource.observeForever {  }

        assertTrue(
            transactionListSource.value?.status == Status.ERROR
                    && transactionListSource.value?.message == errorMsg
        )
    }

    @Test
    fun test_filterTransactions_success() {
        Mockito.`when`(
            repository.getFilteredTransactions(
                anyString(),
                anyBoolean(),
                anyBoolean(),
                anyBoolean()
            )
        ).thenReturn(Observable.just(transactionEntities))

        val transactionsListSource = transactionVM.transactionListSource

        transactionsListSource.observeForever {  }

        transactionVM.filterTransaction(
            credit = false,
            debit = true,
            flagged = true
        )

        assertTrue(
            transactionsListSource.value?.status == Status.SUCCESS
                    && transactionsListSource.value?.data == transactions
        )
    }

    @Test
    fun test_filterTransactions_error() {
        val errorMsg = "fetch transaction error"
        Mockito.`when`(
            repository.getFilteredTransactions(
                anyString(),
                anyBoolean(),
                anyBoolean(),
                anyBoolean()
            )
        ).thenReturn(Observable.error(Throwable(errorMsg)))

        val transactionsListSource = transactionVM.transactionListSource

        transactionsListSource.observeForever {  }

        transactionVM.filterTransaction(
            credit = false,
            debit = true,
            flagged = true
        )

        assertTrue(
            transactionsListSource.value?.status == Status.ERROR
                    && transactionsListSource.value?.message == errorMsg
        )
    }

    @Test
    fun test_transactionStatusUpdater_success() {
        val transaction = transactions[0].copy(flagged = false)
        val transactionEntity = transactionMapper.from(transaction).copy(flagged = true)

        Mockito.`when`(repository.updateTransaction(transactionEntity))
            .thenReturn(Completable.complete())

        transactionVM.toggleFlaggedStatus(transaction)

        Mockito.verify(repository, times(1))
            .updateTransaction(transactionEntity)
    }

    @Test
    fun test_transactionStatusUpdater_error() {
        val errorMsg = "Error in updating transaction"

        val transaction = transactions[0].copy(flagged = false)
        val transactionEntity = transactionMapper.from(transaction).copy(flagged = true)

        Mockito.`when`(repository.updateTransaction(transactionEntity))
            .thenReturn(Completable.error(Throwable(errorMsg)))

        transactionVM.toggleFlaggedStatus(transaction)

        Mockito.verify(repository, times(1))
            .updateTransaction(transactionEntity)
    }

//    Helpers
    private fun getTransactions(): GetUserTransactionsTask {
        return GetUserTransactionsTask(
            repository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    private fun getFilteredTransactions(): FilterTransactionsTask {
        return FilterTransactionsTask(
            repository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    private fun getStatusUpdaterTask(): TransactionStatusUpdaterTask {
        return TransactionStatusUpdaterTask(
            repository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }
}