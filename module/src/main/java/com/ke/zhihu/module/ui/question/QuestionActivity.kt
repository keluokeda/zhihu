package com.ke.zhihu.module.ui.question

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.ke.mvvm.base.ui.BaseViewBindingAdapter
import com.ke.mvvm.base.ui.IBaseRefreshAndLoadMoreView
import com.ke.mvvm.base.ui.launchAndRepeatWithViewLifecycle
import com.ke.zhihu.api.response.AnswerItemResponse
import com.ke.zhihu.module.R
import com.ke.zhihu.module.databinding.ModuleItemAnswerBinding
import com.ke.zhihu.module.databinding.ModuleLayoutQuestionBinding
import com.ke.zhihu.module.databinding.ModuleLayoutToolbarRefreshListBinding
import com.ke.zhihu.module.entity.CommentSortType
import com.ke.zhihu.module.entity.CommentType
import com.ke.zhihu.module.entity.GetCommentsRequest
import com.ke.zhihu.module.toHtml
import com.ke.zhihu.module.utils.ExtraKey
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.lang.IllegalArgumentException

@Route(path = PagePath.PAGE_QUESTION)
@AndroidEntryPoint
class QuestionActivity : AppCompatActivity(), IBaseRefreshAndLoadMoreView {

    private lateinit var binding: ModuleLayoutToolbarRefreshListBinding

    private val questionViewModel: QuestionViewModel by viewModels()

    private val adapter =
        object : BaseViewBindingAdapter<AnswerItemResponse, ModuleItemAnswerBinding>() {
            override fun bindItem(
                item: AnswerItemResponse,
                viewBinding: ModuleItemAnswerBinding,
                viewType: Int,
                position: Int
            ) {
                Glide.with(this@QuestionActivity)
                    .load(item.author.avatarUrl)
                    .into(viewBinding.avatar)
                viewBinding.name.text = item.author.name
                viewBinding.content.text = item.excerpt
                viewBinding.footer.text = item.footerString

                viewBinding.imageList.isVisible = item.thumbnailInfo.thumbnails.isNotEmpty()

                val imageViewList = listOf(
                    viewBinding.image1,
                    viewBinding.image2,
                    viewBinding.image3
                )

                imageViewList.forEach {
                    it.isVisible = false
                }

                if (item.thumbnailInfo.thumbnails.isNotEmpty()) {


                    val imageList = item.thumbnailInfo.thumbnails.take(3).map {
                        it.url
                    }

                    (viewBinding.imageList.layoutParams as? ConstraintLayout.LayoutParams)?.apply {
                        dimensionRatio =
                            when (imageList.size) {
                                1 -> "16:9"
                                2 -> "27:9"
                                3 -> "36:9"
                                else -> throw RuntimeException("错误的image个数${imageList.size}")
                            }
//                            "${imageList.size * 16}:9"
                        viewBinding.imageList.layoutParams = this
                    }

                    imageList.forEachIndexed { index, s ->
                        val imageView = imageViewList[index]
                        imageView.isVisible = true
                        Glide.with(this@QuestionActivity)
                            .load(s)
                            .into(imageView)
                    }

                }
            }

            override fun createViewBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
                viewType: Int
            ): ModuleItemAnswerBinding {
                return ModuleItemAnswerBinding.inflate(inflater, parent, false)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ModuleLayoutToolbarRefreshListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.module_baseline_arrow_back_white_24dp)
            setNavigationOnClickListener {
                onBackPressed()
            }
            this.title = "问题"
        }


        setupRefreshAndLoadMore(
            binding.swipeRefreshLayout,
            questionViewModel,
            adapter,
            binding.recyclerView,
            this
        )

        val questionLayout = ModuleLayoutQuestionBinding.inflate(layoutInflater)

        adapter.addHeaderView(questionLayout.root)

        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position)
            ARouter.getInstance().build(PagePath.PAGE_ANSWER)
//                .withParcelable(
//                    ExtraKey.COMMENTS_REQUEST, GetCommentsRequest(
//                        CommentType.Answers,
//                        item.id.toString()
//                    )
//                )
                .withString(ExtraKey.ID, item.id.toString())
                .navigation()
        }

        launchAndRepeatWithViewLifecycle {
            questionViewModel.questionResponse.collect {
                if (it == null) {
                    questionLayout.root.isVisible = false
                } else {
                    questionLayout.root.isVisible = true
                    questionLayout.title.text = it.title
                    questionLayout.content.text = it.detail.toHtml()
                    binding.toolbar.title = "${it.answerCount}个回答"

                    questionLayout.chipGroup.removeAllViews()

                    it.topics.forEach { topic ->
                        questionLayout.chipGroup.addView(Chip(this@QuestionActivity).apply {
                            this.text = topic.name
                        })
                    }

                    binding.toolbar.apply {
                        menu.clear()
                        menu.add(0, 1, 0, "邀请回答")
                            .setIcon(R.drawable.module_baseline_person_add_white_24dp)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

                        menu.add(0, 2, 0, "写回答")
                            .setIcon(R.drawable.module_baseline_mode_white_24dp)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

                        menu.add(0, 3, 0, "${it.followerCount}关注")
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

                        menu.add(0, 4, 0, "${it.commentCount}回答")
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

                        setOnMenuItemClickListener { menuItem ->

                            when (menuItem.itemId) {

                                3 -> ARouter.getInstance().build(PagePath.PAGE_QUESTION_FOLLOWERS)
                                    .withString(ExtraKey.QUESTION_ID, it.id.toString())
                                    .navigation()

                                4 -> {
                                    ARouter.getInstance().build(PagePath.PAGE_COMMENTS)
                                        .withParcelable(
                                            ExtraKey.COMMENTS_REQUEST, GetCommentsRequest(
                                                CommentType.Questions,
                                                it.id.toString(),
                                                CommentSortType.Default
                                            )
                                        )
                                        .navigation()
                                }

                                else -> throw IllegalArgumentException("非法的 id ${menuItem.itemId}")
                            }
                            true
                        }
                    }
                }
            }
        }

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

    }

    override val emptyLayoutId: Int
        get() = R.layout.module_layout_empty_list
}