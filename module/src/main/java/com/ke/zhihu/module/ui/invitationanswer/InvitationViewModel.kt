package com.ke.zhihu.module.ui.invitationanswer

import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.ui.BaseRefreshAndLoadMoreViewModel
import com.ke.zhihu.api.response.InvitationResponse
import com.ke.zhihu.module.data.UrlStore
import com.ke.zhihu.module.domain.GetInvitationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class InvitationViewModel
@Inject constructor(
    getInvitationListUseCase: GetInvitationListUseCase,
    private val urlStore: UrlStore
) :
    BaseRefreshAndLoadMoreViewModel<Unit, InvitationResponse>(
        getInvitationListUseCase
    ) {
    override val parameters: Unit
        get() = Unit

    init {
        loadData(true)
    }

    override fun onCleared() {
        super.onCleared()
        urlStore.clearInvitationUrl()
    }

    override fun onLoadError(listResult: ListResult<InvitationResponse>) {
        super.onLoadError(listResult)
        listResult.exception?.printStackTrace()
    }
}