package com.ke.zhihu.module.domain

import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.domian.GetDataListUseCase
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.api.response.CommentResponse
import com.ke.zhihu.module.data.UrlStore
import com.ke.zhihu.module.di.IoDispatcher
import com.ke.zhihu.module.entity.CommentSortType
import com.ke.zhihu.module.entity.GetCommentsRequest
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val zhihuApi: ZhihuApi,
    private val urlStore: UrlStore
) : GetDataListUseCase<GetCommentsRequest, CommentResponse>(dispatcher) {

    override suspend fun execute(
        index: Int,
        parameters: GetCommentsRequest
    ): ListResult<CommentResponse> {
        val url = if (index == 1) {
            val urlBuilder = StringBuilder()
            urlBuilder.append("https://api.zhihu.com/comment_v5/")
                .append(parameters.commentType.name.lowercase())
                .append("/")
                .append(parameters.id)
                .append("/root_comment")
            if (parameters.commentSortType == CommentSortType.Latest) {
                urlBuilder.append("?order_by=ts")
            }
            urlBuilder.toString()
        } else {
            urlStore.commentsNextUrl!!
        }

        val response = zhihuApi.getComments(url)
        urlStore.commentsNextUrl = response.paging.next

        return ListResult(
            response.data, response.data.isEmpty()
        )
    }
}

private const val ANSWER_COMMENTS_START_URL =
    "https://api.zhihu.com/comment_v5/answers/2377954285/root_comment?order_by=&type=&section_id="

private const val question_comments_start_url =
    "https://api.zhihu.com/comment_v5/questions/519529414/root_comment?order_by=&type=&section_id="