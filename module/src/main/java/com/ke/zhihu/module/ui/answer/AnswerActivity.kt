package com.ke.zhihu.module.ui.answer

import android.os.Bundle
import android.view.MenuItem
import android.webkit.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.ke.mvvm.base.ui.launchAndRepeatWithViewLifecycle
import com.ke.zhihu.module.R
import com.ke.zhihu.module.databinding.ModuleActivityAnswerBinding
import com.ke.zhihu.module.entity.CommentType
import com.ke.zhihu.module.entity.GetCommentsRequest
import com.ke.zhihu.module.utils.ExtraKey
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
@Route(path = PagePath.PAGE_ANSWER)
class AnswerActivity : AppCompatActivity() {
    private lateinit var binding: ModuleActivityAnswerBinding

    private val viewModel: AnswerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ModuleActivityAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra(ExtraKey.ID)!!

        val url = "https://www.zhihu.com/appview/v2/answer/$id"

        binding.toolbar.apply {
            setNavigationOnClickListener {
                onBackPressed()
            }

            menu.add(0, 1, 0, "评论").setIcon(R.drawable.module_baseline_comment_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

            menu.add(0, 2, 0, "查看问题").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

            setOnMenuItemClickListener {

                when (it.itemId) {
                    1 -> {
                        val getCommentsRequest = GetCommentsRequest(CommentType.Answers, id)
                        ARouter.getInstance().build(PagePath.PAGE_COMMENTS)
                            .withParcelable(ExtraKey.COMMENTS_REQUEST, getCommentsRequest)
                            .navigation()
                    }

                    2 -> {
                        val questionId =
                            viewModel.question.value?.id ?: return@setOnMenuItemClickListener true
                        ARouter.getInstance().build(PagePath.PAGE_QUESTION)
                            .withString(ExtraKey.QUESTION_ID, questionId.toString())
                            .navigation()
                    }
                }



                true
            }
        }



        binding.webView.loadUrl(url)

        binding.webView.webChromeClient = object : WebChromeClient() {

        }


        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.question.collect {
                binding.toolbar.title = it?.title ?: "回答"
            }
        }
    }


    companion object {
        const val EXTRA_URL = "EXTRA_URL"
    }
}