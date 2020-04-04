package com.lukmotech.bankbuddy.domain.usecases

import com.lukmotech.bankbuddy.domain.repository.BankingRepository
import com.lukmotech.bankbuddy.domain.utils.TestDataGenerator
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TestGenerator
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FilterTransactionsTest {
    private lateinit var filterTransactionsTask: FilterTransactionsTask

    @Mock
    private lateinit var bankingRepository: BankingRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        filterTransactionsTask = FilterTransactionsTask(
            bankingRepository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    @Test
    fun test_filterTransactionsTask_success() {
        val accountNumber = "1BFC9A38E6C7"
        val transactions = TestDataGenerator.generateTransactions()

        val isHvt = false
        val isCredit = true
        val isDebit = true

        Mockito.`when`(
            bankingRepository.getFilteredTranscations(
                accountNumber,
                isHvt,
                isCredit,
                isDebit
            )
        ).thenReturn(Observable.just(transactions))

        val testObserver = filterTransactionsTask.buildUseCase(
            FilterTransactionsTask.Params(
                accountNumber,
                isHvt,
                isCredit,
                isDebit
            )
        ).test()

        testObserver.assertSubscribed()
            .assertValue { it.containsAll(transactions)}
            .assertComplete()
    }

    @Test
    fun test_filterTransactionsTask_error() {
        val accountNumber = "1BFC9A38E6C7"
        val errorMsg = "ERROR OCCURRED"

        val isHvt = false
        val isCredit = true
        val isDebit = true

        Mockito.`when`(
            bankingRepository.getFilteredTranscations(
                accountNumber,
                isHvt,
                isCredit,
                isDebit
            )
        ).thenReturn(Observable.error(Throwable(errorMsg)))

        val testObserver = filterTransactionsTask.buildUseCase(
            FilterTransactionsTask.Params(
                accountNumber,
                isHvt,
                isCredit,
                isDebit
            )
        ).test()

        testObserver.assertSubscribed()
            .assertError { it.message?.equals(errorMsg)?: false}
            .assertNotComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_filterTransactionsTaskNoParameters_error() {
        val testObserver = filterTransactionsTask.buildUseCase().test()
        testObserver.assertSubscribed()
    }
}