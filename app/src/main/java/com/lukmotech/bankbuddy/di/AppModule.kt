package com.lukmotech.bankbuddy.di

import android.app.Application
import android.content.Context
import com.lukmotech.bankbuddy.ui.home.MainActivity
import com.lukmotech.bankbuddy.ui.transactions.TransactionListActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {
    @Binds
    abstract fun bindContext(application: Application): Context

    @ContributesAndroidInjector
    internal abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributesTransactionListActivity(): TransactionListActivity
}