package com.ke.zhihu.module.domain

import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.domian.GetDataListUseCase
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.api.response.AnswerItemResponse
import com.ke.zhihu.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetAnswerItemListUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val zhihuApi: ZhihuApi
) :
    GetDataListUseCase<String, AnswerItemResponse>(dispatcher) {
    override suspend fun execute(index: Int, parameters: String): ListResult<AnswerItemResponse> {
        val response = zhihuApi.getAnswerList(parameters, (index - 1) * 5)

        return ListResult(
            response.data, response.data.isEmpty()
        )
    }
}