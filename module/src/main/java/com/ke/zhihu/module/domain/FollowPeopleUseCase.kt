package com.ke.zhihu.module.domain

import com.ke.mvvm.base.domian.UseCase
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FollowPeopleUseCase @Inject constructor(
    private val zhihuApi: ZhihuApi,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, Boolean>(dispatcher) {

    override suspend fun execute(parameters: String): Boolean {
        return zhihuApi.followPeople(parameters).isFollowing
    }
}