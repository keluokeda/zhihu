package com.ke.zhihu.module.domain

import com.ke.mvvm.base.domian.UseCase
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.api.response.QuestionResponse
import com.ke.zhihu.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetQuestionDetailUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val zhihuApi: ZhihuApi
) : UseCase<String, QuestionResponse>(dispatcher) {
    override suspend fun execute(parameters: String): QuestionResponse {
        return zhihuApi.getQuestionDetail(parameters)
    }
}