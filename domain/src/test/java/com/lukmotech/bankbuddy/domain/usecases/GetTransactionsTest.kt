package com.lukmotech.bankbuddy.domain.usecases

import com.lukmotech.bankbuddy.domain.repository.BankingRepository
import com.lukmotech.bankbuddy.domain.utils.TestDataGenerator
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
class GetTransactionsTest {
    private lateinit var getUserTransactionsTask: GetUserTransactionsTask

    @Mock
    private lateinit var bankingRepository: BankingRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getUserTransactionsTask = GetUserTransactionsTask(
            bankingRepository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    @Test
    fun test_getUserTransactions_success() {
        val accountNumber = "1BFC9A38E6C7"
        val limit = 20
        val transactions = TestDataGenerator.generateTransactions()

        Mockito.`when`(bankingRepository.getUserTransactions(accountNumber, limit))
            .thenReturn(Observable.just(transactions))

        val testObserver = getUserTransactionsTask.buildUseCase(
            GetUserTransactionsTask.Params(
                accountNumber,
                limit
            )
        ).test()

        testObserver
            .assertSubscribed()
            .assertValue{ it.containsAll(transactions) }

    }

    @Test
    fun test_getUserTransactions_error() {
        val accountNumber = "1BFC9A38E6C7"
        val limit = 20
        val errorMsg = "ERROR OCCURRED"

        Mockito.`when`(bankingRepository.getUserTransactions(accountNumber, limit))
            .thenReturn(Observable.error(Throwable(errorMsg)))

        val testObserver = getUserTransactionsTask.buildUseCase(
            GetUserTransactionsTask.Params(
                accountNumber,
                limit
            )
        ).test()

        testObserver
            .assertSubscribed()
            .assertError { it.message?.equals(errorMsg,false)?:false }
            .assertNotComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_getUserTransactionsNoParams_error() {
        val testObserver = getUserTransactionsTask.buildUseCase().test()
        testObserver.assertSubscribed()
    }
}