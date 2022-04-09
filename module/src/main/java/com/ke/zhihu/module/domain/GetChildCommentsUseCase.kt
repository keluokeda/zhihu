package com.ke.zhihu.module.domain

import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.domian.GetDataListUseCase
import com.ke.mvvm.base.domian.UseCase
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.api.response.CommentResponse
import com.ke.zhihu.module.data.UrlStore
import com.ke.zhihu.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetChildCommentsUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val zhihuApi: ZhihuApi,
    private val urlStore: UrlStore
) :
    GetDataListUseCase<String, CommentResponse>(dispatcher) {
    override suspend fun execute(index: Int, parameters: String): ListResult<CommentResponse> {
        val url =
            if (index == 1) "https://api.zhihu.com/comment_v5/comment/$parameters/child_comment" else urlStore.childCommentsNextUrl!!

        val response = zhihuApi.getChildComments(url)
        urlStore.childCommentsNextUrl = response.paging.next

        return ListResult(
            response.data, response.data.isEmpty()
        )
    }
}

