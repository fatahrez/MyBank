package com.lukmotech.bankbuddy.data

import com.lukmotech.bankbuddy.data.mapper.TransactionDomainDataMapper
import com.lukmotech.bankbuddy.data.mapper.UserInfoDomainDataMapper
import com.lukmotech.bankbuddy.data.repository.BankingRepositoryImpl
import com.lukmotech.bankbuddy.data.repository.LocalDataSource
import com.lukmotech.bankbuddy.data.repository.RemoteDataSource
import com.lukmotech.bankbuddy.data.utils.TestDataGenerator
import com.lukmotech.bankbuddy.domain.repository.BankingRepository
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

class RepositoryImplementationTest {
    private lateinit var bankingRepository: BankingRepository

    private val userInfoMapper = UserInfoDomainDataMapper()
    private val transactionMapper = TransactionDomainDataMapper()

    @Mock
    private lateinit var localDataSource: LocalDataSource
    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        bankingRepository = BankingRepositoryImpl(
            userInfoMapper,
            transactionMapper,
            localDataSource,
            remoteDataSource
        )
    }

    @Test
    fun test_getUserInfo_local_remote_interaction() {
        val userIdentifier = "1BFC9A38E6C7"
        val userInfoData = TestDataGenerator.generateUserInfo()
        val userInfoDomain = userInfoMapper.from(userInfoData)

        Mockito.`when`(remoteDataSource.getUserInfo(userIdentifier))
            .thenReturn(Observable.just(userInfoData))
        Mockito.`when`(localDataSource.getUserInfo(userIdentifier))
            .thenReturn(Observable.just(userInfoData))

        val testSubscriber = bankingRepository.getUserInfo(userIdentifier).test()

        testSubscriber.assertSubscribed()
            .assertValueCount(2)
            .assertValues(userInfoDomain, userInfoDomain)
            .assertComplete()

        Mockito.verify(localDataSource, times(1))
            .saveUserInfo(userInfoData)

        Mockito.verify(remoteDataSource, times(1))
            .getUserInfo(userIdentifier)
    }

    @Test
    fun test_getUserInfo_remote_error() {
        val userIdentifier = "1BFC9A38E6C7"
        val userInfoData = TestDataGenerator.generateUserInfo()
        val userInfoDomain = userInfoMapper.from(userInfoData)

        Mockito.`when`(remoteDataSource.getUserInfo(userIdentifier))
            .thenReturn(Observable.error(Throwable()))
        Mockito.`when`(localDataSource.getUserInfo(userIdentifier))
            .thenReturn(Observable.just(userInfoData))

        val testSubscriber = bankingRepository.getUserInfo(userIdentifier).test()

        testSubscriber.assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == userInfoDomain
            }
            .assertComplete()

        Mockito.verify(localDataSource, times(1))
            .getUserInfo(userIdentifier)
    }

    @Test
    fun test_getUserTransactions_local_remote_interactions() {
        val userIdentifier = "1BFC9A38E6C7"
        val limit = 20
        val transactionsData = TestDataGenerator.generateTransactions()

        Mockito.`when`(remoteDataSource.getUserTransaction(userIdentifier, limit))
            .thenReturn(Observable.just(transactionsData))

        Mockito.`when`(localDataSource.getUserTransactions(userIdentifier, limit))
            .thenReturn(Observable.just(transactionsData))

        val testSubscriber = bankingRepository.getUserTransactions(userIdentifier, limit).test()

        testSubscriber.assertSubscribed()
            .assertValues(
                transactionsData.map { transactionMapper.from(it) },
                transactionsData.map { transactionMapper.from(it) }
            )
            .assertComplete()

        Mockito.verify(localDataSource, times(1))
            .saveUserTransactions(userIdentifier, transactionsData)

        Mockito.verify(remoteDataSource, times(1))
            .getUserTransaction(userIdentifier, limit)

    }

    @Test
    fun test_getUserTransactions_remote_error() {
        val userIdentifier = "1BFC9A38E6C7"
        val limit = 20
        val transactionsData = TestDataGenerator.generateTransactions()

        Mockito.`when`(remoteDataSource.getUserTransaction(userIdentifier, limit))
            .thenReturn(Observable.error(Throwable()))

        Mockito.`when`(localDataSource.getUserTransactions(userIdentifier, limit))
            .thenReturn(Observable.just(transactionsData))

        val testSubscriber = bankingRepository.getUserTransactions(userIdentifier, limit).test()

        testSubscriber.assertSubscribed()
            .assertValue { transactions ->
                transactions.containsAll(transactionsData.map {
                    transactionMapper.from(it)
                })
            }
            .assertComplete()

        Mockito.verify(localDataSource, times(1))
            .getUserTransactions(userIdentifier,limit)
    }

    @Test
    fun test_getFilteredTransactions_local_interactions() {
        val userIdentifier = "1BFC9A38E6C7"
        val flagged = true
        val credit = false
        val debit = true
        val transactionsData = TestDataGenerator.generateTransactions()

        Mockito.`when`(
            localDataSource.getFilteredTransactions(
                userIdentifier, credit, debit, flagged
            )
        ).thenReturn(Observable.just(transactionsData))

        val testSubscriber = bankingRepository.getFilteredTransactions(
            userIdentifier, credit, debit, flagged
        ).test()

        testSubscriber.assertSubscribed()
            .assertValue { transactions ->
                transactions.containsAll(transactionsData.map { transactionMapper.from(it) })
            }

        Mockito.verify(localDataSource, times(1))
            .getFilteredTransactions(
                userIdentifier, credit, debit, flagged
            )
    }

    @Test
    fun test_updateFlaggedStatus_local_interaction() {
        val transactionData = TestDataGenerator.generateTransactions()[0]
        val transactionEntity = transactionMapper.from(transactionData)

        Mockito.`when`(localDataSource.updateTransaction(transactionData))
            .thenReturn(Completable.complete())

        val testObserver = bankingRepository.updateTransaction(transactionEntity).test()

        testObserver.assertSubscribed()

        Mockito.verify(localDataSource, times(1))
            .updateTransaction(transactionData)
    }
}