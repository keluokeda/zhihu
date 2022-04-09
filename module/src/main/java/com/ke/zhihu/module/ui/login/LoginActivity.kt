package com.ke.zhihu.module.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.ke.mvvm.base.ui.collectLoadingDialog
import com.ke.mvvm.base.ui.collectSnackbarFlow
import com.ke.mvvm.base.ui.launchAndRepeatWithViewLifecycle
import com.ke.zhihu.module.MainApplication
import com.ke.zhihu.module.R
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@Route(path = PagePath.PAGE_LOGIN)
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_activity_login)



        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, LoginFragment())
                .commit()
        }

        collectSnackbarFlow(loginViewModel)
        collectLoadingDialog(loginViewModel)


        launchAndRepeatWithViewLifecycle {
            loginViewModel.navigationActions.collect {
                when (it) {
                    LoginNavigationActions.ShowProfileFragment -> {
                        supportFragmentManager.beginTransaction()

                            .replace(R.id.fragment_container, ProfileFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        }
    }


}