package com.lukmotech.bankbuddy.application

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BankBuddyApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerBankBudyAppComponent.builder().application(this).build()
    }
}