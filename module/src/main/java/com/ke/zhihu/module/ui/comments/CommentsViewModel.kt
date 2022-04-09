package com.ke.zhihu.module.ui.comments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.ui.BaseRefreshAndLoadMoreViewModel
import com.ke.zhihu.api.response.CommentResponse
import com.ke.zhihu.module.domain.GetCommentsUseCase
import com.ke.zhihu.module.entity.CommentSortType
import com.ke.zhihu.module.entity.GetCommentsRequest
import com.ke.zhihu.module.utils.ExtraKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    getCommentsUseCase: GetCommentsUseCase,
    savedStateHandle: SavedStateHandle
) :
    BaseRefreshAndLoadMoreViewModel<GetCommentsRequest, CommentResponse>(getCommentsUseCase) {


    private val _sortType = MutableStateFlow(CommentSortType.Default)

    internal val sortType: StateFlow<CommentSortType>
        get() = _sortType
    override val parameters: GetCommentsRequest
        get() = GetCommentsRequest(
            getCommentsRequest.commentType,
            getCommentsRequest.id,
            sortType.value
        )

    internal fun setSortType(commentSortType: CommentSortType) {
        _sortType.value = commentSortType
    }

    private val getCommentsRequest =
        savedStateHandle.get<GetCommentsRequest>(ExtraKey.COMMENTS_REQUEST)!!

    init {
//        refresh()
        viewModelScope.launch {
            _sortType.collect {
                refresh()
            }
        }
    }

    override fun onLoadError(listResult: ListResult<CommentResponse>) {
        super.onLoadError(listResult)
        listResult.exception?.printStackTrace()
    }


}