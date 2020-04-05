package com.lukmotech.bankbuddy.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.lukmotech.bankbuddy.domain.entities.UserInfoEntity
import com.lukmotech.bankbuddy.domain.usecases.GetUserInfoTask
import com.lukmotech.bankbuddy.presentation.mapper.Mapper
import com.lukmotech.bankbuddy.presentation.model.Resource
import com.lukmotech.bankbuddy.presentation.model.UserInfo
import com.lukmotech.bankbuddy.presentation.qualifier.UserIdentity
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class HomeVM @Inject internal constructor(
    @UserIdentity private val userIdentifier: String,
    private val userInfoMapper: Mapper<UserInfoEntity, UserInfo>,
    private val getUserInfoTask: GetUserInfoTask
): ViewModel(){
    val getUserInfoResource: LiveData<Resource<UserInfo>>
        get() = getUserInfoTask
            .buildUseCase(userIdentifier)
            .map { userInfoMapper.to(it) }
            .map { Resource.success(it) }
            .startWith(Resource.loading())
            .onErrorResumeNext(
                Function {
                    Observable.just(Resource.error(it.localizedMessage))
                }
            ).toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
}