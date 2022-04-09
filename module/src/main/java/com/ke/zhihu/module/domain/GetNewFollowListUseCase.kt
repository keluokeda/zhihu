package com.ke.zhihu.module.domain

import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.domian.GetDataListUseCase
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.api.response.InvitationResponse
import com.ke.zhihu.module.data.UrlStore
import com.ke.zhihu.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetNewFollowListUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val urlStore: UrlStore,
    private val zhihuApi: ZhihuApi
) :
    GetDataListUseCase<Unit, InvitationResponse>(dispatcher) {

    override suspend fun execute(index: Int, parameters: Unit): ListResult<InvitationResponse> {

        val url = if (index == 1) START_URL else urlStore.newFollowNextUrl ?: START_URL

        val response = zhihuApi.getNewFollowList(url)
        urlStore.newFollowNextUrl = response.paging.next

        return ListResult(
            response.data,
            over = response.data.isEmpty()
        )
    }
}

private const val START_URL =
    "https://api.zhihu.com/notifications/v3/timeline/entry/follow?limit=20&adr_com=5"