package com.ke.zhihu.module.domain

import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.domian.GetDataListUseCase
import com.ke.mvvm.base.domian.UseCase
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.api.response.HomeArticleResponse
import com.ke.zhihu.module.data.UrlStore
import com.ke.zhihu.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetHomeArticleListUseCase @Inject constructor(
    private val zhihuApi: ZhihuApi,
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val urlStore: UrlStore
) :
    GetDataListUseCase<Unit, HomeArticleResponse>(dispatcher) {

    override suspend fun execute(index: Int, parameters: Unit): ListResult<HomeArticleResponse> {

        val url = (if (index == 1) urlStore.homePreviousUrl else urlStore.homeNextUrl) ?: URL
        val response = zhihuApi.getHomeArticleList(url)
        urlStore.homeNextUrl = response.paging.next
        urlStore.homePreviousUrl = response.paging.previous

        return ListResult(
            response.data.filter {
                it.commonCard.feedContent.video == null
            }
        )
    }

    companion object {
        private const val URL =
            "https://api.zhihu.com/topstory/recommend?action=down&scroll=up&limit=10&start_type=cold&tsp_ad_cardredesign=0&feed_card_exp=card_corner%7C1&v_serial=1&isDoubleFlow=0"
    }
}