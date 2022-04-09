package com.ke.zhihu.module.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ke.zhihu.module.data.UserDataStore
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(userDataStore: UserDataStore) : ViewModel() {

    private val _navigationActions = Channel<SplashNavigationActions>(capacity = Channel.CONFLATED)

    val navigationActions: Flow<SplashNavigationActions>
        get() = _navigationActions.receiveAsFlow()

    init {
        viewModelScope.launch {
            _navigationActions.send(if (userDataStore.authorization == null) SplashNavigationActions.NavigateToLogin else SplashNavigationActions.NavigateToMain)
        }
    }
}

sealed interface SplashNavigationActions {
    val path: String

    object NavigateToMain : SplashNavigationActions {
        override val path: String
            get() = PagePath.PAGE_MAIN
    }

    object NavigateToLogin : SplashNavigationActions {
        override val path: String
            get() = PagePath.PAGE_LOGIN
    }
}