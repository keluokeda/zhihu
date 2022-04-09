package com.ke.zhihu.module.ui.people

import androidx.lifecycle.SavedStateHandle
import com.ke.mvvm.base.data.ListResult
import com.ke.zhihu.api.response.PeopleResponse
import com.ke.zhihu.module.domain.GetPeopleFollowersUseCase
import com.ke.zhihu.module.domain.GetQuestionFollowersUseCase
import com.ke.zhihu.module.utils.ExtraKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionFollowersViewModel @Inject constructor(
    getQuestionFollowersUseCase: GetQuestionFollowersUseCase,
    savedStateHandle: SavedStateHandle
) :
    BasePeopleListViewModel<String>(getQuestionFollowersUseCase) {
    override val parameters: String = savedStateHandle.get<String>(ExtraKey.QUESTION_ID)!!


    init {
        refresh()
    }


}