package com.lukmotech.bankbuddy.application

import com.lukmotech.bankbuddy.di.DaggerBankBudyAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BankBuddyApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerBankBudyAppComponent.builder().application(this).build()
    }
}