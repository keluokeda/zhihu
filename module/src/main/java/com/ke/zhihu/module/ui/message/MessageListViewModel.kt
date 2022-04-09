package com.ke.zhihu.module.ui.message

import androidx.lifecycle.viewModelScope
import com.ke.mvvm.base.model.SnackbarAction
import com.ke.mvvm.base.ui.BaseRefreshAndLoadMoreViewModel
import com.ke.zhihu.api.response.InvitationResponse
import com.ke.zhihu.module.domain.GetMessageListUseCase
import com.ke.zhihu.module.domain.ReadAllMessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageListViewModel @Inject constructor(
    getMessageListUseCase: GetMessageListUseCase,
    private val readAllMessagesUseCase: ReadAllMessagesUseCase
) :
    BaseRefreshAndLoadMoreViewModel<Unit, InvitationResponse>(getMessageListUseCase) {
    override val parameters: Unit
        get() = Unit

    init {
        loadData(true)
    }


    internal fun readAll() {
        viewModelScope.launch {
            readAllMessagesUseCase(Unit)
            showSnackbar(SnackbarAction("操作成功"))

        }
    }
}