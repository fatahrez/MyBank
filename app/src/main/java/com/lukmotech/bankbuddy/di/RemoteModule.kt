package com.lukmotech.bankbuddy.di

import com.lukmotech.bankbuddy.BuildConfig
import com.lukmotech.bankbuddy.data.model.TransactionData
import com.lukmotech.bankbuddy.data.model.UserInfoData
import com.lukmotech.bankbuddy.data.repository.RemoteDataSource
import com.lukmotech.remote.api.BankingService
import com.lukmotech.remote.mapper.Mapper
import com.lukmotech.remote.mapper.TransactionDataNetworkMapper
import com.lukmotech.remote.mapper.UserInfoDataNetworkMapper
import com.lukmotech.remote.model.TransactionNetwork
import com.lukmotech.remote.model.UserInfoNetwork
import com.lukmotech.remote.source.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [RemoteModule.Binders::class])
class RemoteModule {

    @Module
    interface Binders {

        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: RemoteDataSourceImpl
        ): RemoteDataSource

        @Binds
        fun bindUserInfoMapper(
            userInfoMapper: UserInfoDataNetworkMapper
        ): Mapper<UserInfoData, UserInfoNetwork>

        @Binds
        fun bindTransactionMapper(
            transactionMapper: TransactionDataNetworkMapper
        ): Mapper<TransactionData, TransactionNetwork>
    }

    @Provides
    fun providesBankingService(retrofit: Retrofit): BankingService = retrofit.create(BankingService::class.java)

    @Provides
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
}