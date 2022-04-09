package com.ke.zhihu.module.ui.answer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ke.mvvm.base.data.Result
import com.ke.zhihu.api.response.QuestionResponse
import com.ke.zhihu.module.domain.GetQuestionByAnswerIdUseCase
import com.ke.zhihu.module.utils.ExtraKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(
    private val getQuestionByAnswerIdUseCase: GetQuestionByAnswerIdUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val answerId = savedStateHandle.get<String>(ExtraKey.ID)!!

    private val _question = MutableStateFlow<QuestionResponse?>(null)

    internal val question: StateFlow<QuestionResponse?>
        get() = _question

    init {
        viewModelScope.launch {
            val result = getQuestionByAnswerIdUseCase(answerId)

            when (result) {
                is Result.Success -> {
                    _question.value = result.data
                }
                is Result.Error -> {
                    result.exception.printStackTrace()
                }
            }
        }
    }
}