package com.ke.zhihu.module.ui.comments

import androidx.lifecycle.SavedStateHandle
import com.ke.mvvm.base.ui.BaseRefreshAndLoadMoreViewModel
import com.ke.zhihu.api.response.CommentResponse
import com.ke.zhihu.module.domain.GetChildCommentsUseCase
import com.ke.zhihu.module.utils.ExtraKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChildCommentsViewModel @Inject constructor(
    getChildCommentsUseCase: GetChildCommentsUseCase,
    savedStateHandle: SavedStateHandle
) :
    BaseRefreshAndLoadMoreViewModel<String, CommentResponse>(getChildCommentsUseCase) {

    override val parameters: String
        get() = commentResponse.id

    internal val commentResponse = savedStateHandle.get<CommentResponse>(ExtraKey.COMMENT)!!


    init {
        refresh()
    }
}