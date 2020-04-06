package com.lukmotech.bankbuddy.di

import android.app.Application
import com.lukmotech.bankbuddy.application.BankBuddyApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        DomainModule::class,
        LocalPersistenceModule::class,
        RemoteModule::class,
        IdentityModule::class,
        PresentationModule::class,
        AppModule::class
    ]
)
interface BankBudyAppComponent : AndroidInjector<BankBuddyApp>{
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): BankBudyAppComponent
    }

    override fun inject(app: BankBuddyApp)
}