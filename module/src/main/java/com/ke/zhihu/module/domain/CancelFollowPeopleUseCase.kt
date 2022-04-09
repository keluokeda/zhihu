package com.ke.zhihu.module.domain

import com.ke.mvvm.base.domian.UseCase
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.module.data.UserDataStore
import com.ke.zhihu.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CancelFollowPeopleUseCase @Inject constructor(
    private val zhihuApi: ZhihuApi,
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val userDataStore: UserDataStore
) : UseCase<String, Boolean>(dispatcher) {

    override suspend fun execute(parameters: String): Boolean {
        return zhihuApi.cancelFollowPeople(parameters, userDataStore.loginUserId!!).isFollowing
    }
}