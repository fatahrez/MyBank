package com.lukmotech.remote.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object HttpClient {
    fun setupOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}