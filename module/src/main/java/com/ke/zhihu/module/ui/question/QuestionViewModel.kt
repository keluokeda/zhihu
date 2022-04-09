package com.ke.zhihu.module.ui.question

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ke.mvvm.base.data.ListResult
import com.ke.mvvm.base.data.Result
import com.ke.mvvm.base.ui.BaseRefreshAndLoadMoreViewModel
import com.ke.zhihu.api.response.AnswerItemResponse
import com.ke.zhihu.api.response.QuestionResponse
import com.ke.zhihu.module.domain.GetAnswerItemListUseCase
import com.ke.zhihu.module.domain.GetQuestionDetailUseCase
import com.ke.zhihu.module.utils.ExtraKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    getAnswerItemListUseCase: GetAnswerItemListUseCase,
    private val getQuestionDetailUseCase: GetQuestionDetailUseCase,
    savedStateHandle: SavedStateHandle
) :
    BaseRefreshAndLoadMoreViewModel<String, AnswerItemResponse>(getAnswerItemListUseCase) {
    override val parameters: String
        get() = questionId

    private val questionId = savedStateHandle.get<String>(ExtraKey.QUESTION_ID)!!

//    private val _title = MutableStateFlow("")
//
//    internal val title: StateFlow<String>
//        get() = _title

    private val _questionResponse = MutableStateFlow<QuestionResponse?>(null)

    internal val questionResponse: StateFlow<QuestionResponse?>
        get() = _questionResponse

    init {
        refresh()
    }

    override fun refresh() {
        super.refresh()

        loadQuestionDetail()
    }

    private fun loadQuestionDetail() {

        viewModelScope.launch {
            val result = getQuestionDetailUseCase(parameters)

            _questionResponse.value = when (result) {
                is Result.Success -> result.data
                is Result.Error -> null
            }
        }
    }


    override fun onLoadError(listResult: ListResult<AnswerItemResponse>) {
        super.onLoadError(listResult)
        listResult.exception?.printStackTrace()
    }
}