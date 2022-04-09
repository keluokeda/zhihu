package com.ke.zhihu.module.domain

import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.domian.GetDataListUseCase
import com.ke.mvvm.base.domian.UseCase
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.api.response.PeopleResponse
import com.ke.zhihu.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetQuestionFollowersUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val zhihuApi: ZhihuApi
) :
    GetDataListUseCase<String, PeopleResponse>(dispatcher) {
    override suspend fun execute(index: Int, parameters: String): ListResult<PeopleResponse> {
        val response = zhihuApi.getQuestionFollowers(parameters, (index - 1) * 20)

        return ListResult(
            response.data, response.data.isEmpty()
        )
    }

}