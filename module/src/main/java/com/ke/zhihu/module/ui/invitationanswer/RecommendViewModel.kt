package com.ke.zhihu.module.ui.invitationanswer

import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.ui.BaseRefreshAndLoadMoreViewModel
import com.ke.zhihu.api.response.InvitationAnswerResponse
import com.ke.zhihu.module.domain.GetRecommendInvitationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class RecommendViewModel @Inject constructor(getRecommendInvitationListUseCase: GetRecommendInvitationListUseCase) :
    BaseRefreshAndLoadMoreViewModel<Unit, InvitationAnswerResponse>(
        getRecommendInvitationListUseCase
    ) {
    override val parameters: Unit
        get() = Unit

    init {
        loadData(true)
    }

    override fun onLoadError(listResult: ListResult<InvitationAnswerResponse>) {
        super.onLoadError(listResult)
    }
}