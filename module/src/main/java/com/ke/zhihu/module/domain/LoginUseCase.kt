package com.ke.zhihu.module.domain

import com.ke.mvvm.base.domian.UseCase
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.api.response.PeopleResponse
import com.ke.zhihu.module.data.UserDataStore
import com.ke.zhihu.module.di.IoDispatcher
import com.ke.zhihu.module.entity.LoginRequest
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val zhihuApi: ZhihuApi,
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val userDataStore: UserDataStore
) : UseCase<LoginRequest, PeopleResponse>(dispatcher) {

    override suspend fun execute(parameters: LoginRequest): PeopleResponse {
        userDataStore.authorization = parameters.authorization
        return zhihuApi.checkToken()
    }
}