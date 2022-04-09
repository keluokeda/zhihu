package com.ke.zhihu.module.ui.main

import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.ui.BaseRefreshAndLoadMoreViewModel
import com.ke.zhihu.api.response.HomeArticleResponse
import com.ke.zhihu.module.data.UrlStore
import com.ke.zhihu.module.domain.GetHomeArticleListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getHomeArticleListUseCase: GetHomeArticleListUseCase,
    private val urlStore: UrlStore
) :
    BaseRefreshAndLoadMoreViewModel<Unit, HomeArticleResponse>(getHomeArticleListUseCase) {
    override val parameters: Unit
        get() = Unit

    init {
        refresh()
    }

    override fun onLoadError(listResult: ListResult<HomeArticleResponse>) {
        super.onLoadError(listResult)
        listResult.exception?.printStackTrace()
    }

    override fun onCleared() {
        super.onCleared()
        urlStore.clearHomeUrl()
    }
}