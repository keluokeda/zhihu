package com.ke.zhihu.module.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.ke.mvvm.base.ui.launchAndRepeatWithViewLifecycle
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        launchAndRepeatWithViewLifecycle {
            splashViewModel.navigationActions.collect {
                ARouter.getInstance().build(it.path)
                    .navigation()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}