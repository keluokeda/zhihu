package com.ke.zhihu.module.ui.like

import com.ke.mvvm.base.ui.BaseRefreshAndLoadMoreViewModel
import com.ke.zhihu.api.response.InvitationResponse
import com.ke.zhihu.module.domain.GetLikeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LikeListViewModel @Inject constructor(getLikeListUseCase: GetLikeListUseCase) :
    BaseRefreshAndLoadMoreViewModel<Unit, InvitationResponse>(getLikeListUseCase) {
    override val parameters: Unit
        get() = Unit

    init {
        loadData(true)
    }
}