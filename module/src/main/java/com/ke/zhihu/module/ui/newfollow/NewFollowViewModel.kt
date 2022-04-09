package com.ke.zhihu.module.ui.newfollow

import com.ke.mvvm.base.ui.BaseRefreshAndLoadMoreViewModel
import com.ke.zhihu.api.response.InvitationResponse
import com.ke.zhihu.module.domain.GetNewFollowListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewFollowViewModel @Inject constructor(getNewFollowListUseCase: GetNewFollowListUseCase) :
    BaseRefreshAndLoadMoreViewModel<Unit, InvitationResponse>(getNewFollowListUseCase) {
    override val parameters: Unit
        get() = Unit

    init {
        loadData(true)
    }
}