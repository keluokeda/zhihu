package com.ke.zhihu.module.domain

import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.domian.GetDataListUseCase
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.api.response.InvitationResponse
import com.ke.zhihu.module.data.UrlStore
import com.ke.zhihu.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetInvitationListUseCase @Inject constructor(
    private val zhihuApi: ZhihuApi,
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val urlStore: UrlStore
) : GetDataListUseCase<Unit, InvitationResponse>(dispatcher) {

    override suspend fun execute(index: Int, parameters: Unit): ListResult<InvitationResponse> {
        val url = (if (index == 1) urlStore.invitationPreviousUrl else urlStore.invitationNextUrl)
            ?: "https://api.zhihu.com/notifications/v3/timeline/entry/invite?title=%E9%82%80%E8%AF%B7%E5%9B%9E%E7%AD%94&invite_with_time_slice=1&extra_from=invite&version_v2=1&toolbar_visibility=false"

        val response = zhihuApi.getInvitationList(url)

        urlStore.invitationPreviousUrl = response.paging.previous
        urlStore.invitationNextUrl = response.paging.next

        return ListResult(response.data.filter {
            it.emptyInfo.isEmpty()
        })
    }

}