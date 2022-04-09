package com.ke.zhihu.module.ui.login

import androidx.lifecycle.viewModelScope
import com.ke.mvvm.base.data.Result
import com.ke.mvvm.base.model.SnackbarAction
import com.ke.mvvm.base.ui.BaseViewModel
import com.ke.zhihu.api.response.PeopleResponse
import com.ke.zhihu.module.data.UserDataStore
import com.ke.zhihu.module.domain.LoginUseCase
import com.ke.zhihu.module.entity.HttpResponse
import com.ke.zhihu.module.entity.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userDataStore: UserDataStore
) : BaseViewModel() {


    private val _navigationActions = Channel<LoginNavigationActions>(capacity = Channel.CONFLATED)

    val navigationActions: Flow<LoginNavigationActions>
        get() = _navigationActions.receiveAsFlow()


    lateinit var currentUser: PeopleResponse

    internal fun login(authorization: String) {
        viewModelScope.launch {

            showLoadingDialog("提交中")

            val result = loginUseCase(LoginRequest(authorization))
            dismissLoadingDialog()
            when (result) {
                is Result.Success -> {
                    currentUser = result.data
                    _navigationActions.send(LoginNavigationActions.ShowProfileFragment)
                }
                is Result.Error -> {
                    showSnackbar(SnackbarAction("请求失败"))
                }
            }
        }
    }

    /**
     * 保存用户id
     */
    internal fun saveUserId() {
        userDataStore.loginUserId = currentUser.id
    }
}

sealed interface LoginNavigationActions {
    object ShowProfileFragment : LoginNavigationActions
}