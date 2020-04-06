package com.lukmotech.bankbuddy.di

import com.lukmotech.bankbuddy.data.mapper.Mapper
import com.lukmotech.bankbuddy.data.mapper.TransactionDomainDataMapper
import com.lukmotech.bankbuddy.data.mapper.UserInfoDomainDataMapper
import com.lukmotech.bankbuddy.data.model.TransactionData
import com.lukmotech.bankbuddy.data.model.UserInfoData
import com.lukmotech.bankbuddy.data.repository.BankingRepositoryImpl
import com.lukmotech.bankbuddy.domain.entities.TransactionEntity
import com.lukmotech.bankbuddy.domain.entities.UserInfoEntity
import com.lukmotech.bankbuddy.domain.repository.BankingRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {
    @Binds
    abstract fun bindsRepository(
        repoImpl: BankingRepositoryImpl
    ): BankingRepository

    @Binds
    abstract fun bindUserMapper(
        mapper: UserInfoDomainDataMapper
    ): Mapper<UserInfoEntity, UserInfoData>

    @Binds
    abstract fun bindsTransactionMapper (
        mapper: TransactionDomainDataMapper
    ): Mapper<TransactionEntity, TransactionData>
}