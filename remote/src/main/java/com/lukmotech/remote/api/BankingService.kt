package com.lukmotech.remote.api

import com.lukmotech.remote.model.ResponseWrapper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface BankingService {
    @GET("assets/bankassist/{identifier}.json")
    fun getUserInformation(@Path("identifier") useridentifier: String):
            Observable<ResponseWrapper>
}