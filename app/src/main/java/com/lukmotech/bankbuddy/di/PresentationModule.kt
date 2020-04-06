package com.lukmotech.bankbuddy.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lukmotech.bankbuddy.domain.entities.TransactionEntity
import com.lukmotech.bankbuddy.domain.entities.UserInfoEntity
import com.lukmotech.bankbuddy.presentation.factory.ViewModelFactory
import com.lukmotech.bankbuddy.presentation.mapper.Mapper
import com.lukmotech.bankbuddy.presentation.mapper.TransactionEntityMapper
import com.lukmotech.bankbuddy.presentation.mapper.UserInfoEntityMapper
import com.lukmotech.bankbuddy.presentation.model.Transaction
import com.lukmotech.bankbuddy.presentation.model.UserInfo
import com.lukmotech.bankbuddy.presentation.viewmodels.HomeVM
import com.lukmotech.bankbuddy.presentation.viewmodels.TransactionVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {
    @Binds
    abstract fun bindsViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeVM::class)
    abstract fun bindsHomeViewModel(homeVM: HomeVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionVM::class)
    abstract fun bindsTransactionsViewModel(transactionVM: TransactionVM): ViewModel

    @Binds
    abstract fun bindsUserInfoMapper(
        userInfoEntityMapper: UserInfoEntityMapper
    ): Mapper<UserInfoEntity, UserInfo>

    @Binds
    abstract fun bindsTransactionMapper(
        transactionEntityMapper: TransactionEntityMapper
    ): Mapper<TransactionEntity, Transaction>
}