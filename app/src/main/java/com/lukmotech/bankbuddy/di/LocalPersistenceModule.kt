package com.lukmotech.bankbuddy.di

import android.app.Application
import com.lukmotech.bankbuddy.data.model.TransactionData
import com.lukmotech.bankbuddy.data.model.UserInfoData
import com.lukmotech.bankbuddy.data.repository.LocalDataSource
import com.lukmotech.bankbuddy.local.database.MyBankDB
import com.lukmotech.bankbuddy.local.mapper.Mapper
import com.lukmotech.bankbuddy.local.mapper.TransactionDataLocalMapper
import com.lukmotech.bankbuddy.local.mapper.UserInfoDataLocalMapper
import com.lukmotech.bankbuddy.local.model.TransactionLocal
import com.lukmotech.bankbuddy.local.model.UserInfoLocal
import com.lukmotech.bankbuddy.local.source.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LocalPersistenceModule.Binders::class])
class LocalPersistenceModule {
    @Module
    interface Binders {
        @Binds
        fun bindsLocalDataSource(
            localDataSourceImpl: LocalDataSourceImpl
        ): LocalDataSource

        @Binds
        fun bindUserInfoMapper(
            userInfoMapper: UserInfoDataLocalMapper
        ): Mapper<UserInfoData, UserInfoLocal>

        @Binds
        fun bindTransactionMapper(
            transactionMapper: TransactionDataLocalMapper
        ): Mapper<TransactionData, TransactionLocal>
    }

    @Provides
    @Singleton
    fun providesDatase(
        application: Application
    ) = MyBankDB.getInstance(application.applicationContext)

    @Provides
    @Singleton
    fun providesUserInfoDAO(
        bankDB: MyBankDB
    ) = bankDB.getUserInfoDao()

    @Provides
    @Singleton
    fun providesTransactionDAO(
        bankDB: MyBankDB
    ) = bankDB.getTransactionDao()
}