package com.ke.zhihu.module

import android.app.Application
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import com.alibaba.android.arouter.launcher.ARouter
import dagger.hilt.android.HiltAndroidApp
import java.text.SimpleDateFormat
import java.util.*

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
    }


}

fun String.toHtml(): Spanned {
    return Html.fromHtml(this)
}

fun Long.toDateString(): String {
    return simpleDateFormat.format(Date(this * 1000))
}

private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")