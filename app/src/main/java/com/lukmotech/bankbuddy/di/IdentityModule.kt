package com.lukmotech.bankbuddy.di

import com.lukmotech.bankbuddy.BuildConfig
import com.lukmotech.bankbuddy.presentation.qualifier.UserIdentity
import dagger.Module
import dagger.Provides

@Module
class IdentityModule {
    @Provides
    @UserIdentity
    fun providesUserID(): String = BuildConfig.USER_ID
}