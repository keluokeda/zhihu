package com.ke.zhihu.module.domain

import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.domian.GetDataListUseCase
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.api.response.InvitationAnswerResponse
import com.ke.zhihu.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetRecommendInvitationListUseCase @Inject constructor(
    private val zhihuApi: ZhihuApi,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : GetDataListUseCase<Unit, InvitationAnswerResponse>(dispatcher) {

    override suspend fun execute(
        index: Int,
        parameters: Unit
    ): ListResult<InvitationAnswerResponse> {
        val list = zhihuApi.getRecommendInvitationList((index - 1) * 20).data

        return ListResult(list)
    }
}