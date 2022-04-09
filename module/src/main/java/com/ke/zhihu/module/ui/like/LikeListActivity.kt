package com.ke.zhihu.module.ui.like

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.ke.mvvm.base.ui.BaseViewBindingAdapter
import com.ke.mvvm.base.ui.IBaseRefreshAndLoadMoreView
import com.ke.zhihu.api.response.InvitationResponse
import com.ke.zhihu.module.R
import com.ke.zhihu.module.databinding.ModuleActivityLikeListBinding
import com.ke.zhihu.module.databinding.ModuleItemLikeBinding
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint

@Route(path = PagePath.PAGE_LIKE_LIST)
@AndroidEntryPoint
class LikeListActivity : AppCompatActivity(), IBaseRefreshAndLoadMoreView {

    private val viewModel: LikeListViewModel by viewModels()

    private lateinit var binding: ModuleActivityLikeListBinding

    private val adapter =
        object : BaseViewBindingAdapter<InvitationResponse, ModuleItemLikeBinding>() {
            override fun bindItem(
                item: InvitationResponse,
                viewBinding: ModuleItemLikeBinding,
                viewType: Int,
                position: Int
            ) {
                if (item.emptyInfo.isNotEmpty()) {
                    viewBinding.header.isVisible = true
                    viewBinding.cardAnswer.isVisible = false
                    viewBinding.cardComment.isVisible = false
                    viewBinding.header.text = item.emptyInfo["text"]
                    viewBinding.group.isVisible = false
                } else {
                    viewBinding.header.isVisible = false
                    viewBinding.group.isVisible = true


                    Glide.with(this@LikeListActivity)
                        .load(item.head.avatarUrls.firstOrNull())
                        .into(viewBinding.avatar)
                    viewBinding.name.text = item.content.title
                    viewBinding.subTitle.text = item.content.subTitle
                    viewBinding.date.text = item.date
                    if (item.content.subText.isEmpty()) {
                        //回答
                        viewBinding.cardAnswer.isVisible = true
                        viewBinding.cardComment.isVisible = false
                        viewBinding.answerText.text = item.targetSource.text
                        viewBinding.answerSubText.text = item.targetSource.subText

                    } else {
                        //评论
                        viewBinding.cardAnswer.isVisible = false
                        viewBinding.cardComment.isVisible = true
                        viewBinding.commentText.text = Html.fromHtml(item.content.subText)
                        viewBinding.commentAnswerText.text = item.targetSource.text
                        viewBinding.commentAnswerSubText.text = item.targetSource.subText
                    }
                }
            }


            override fun createViewBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
                viewType: Int
            ): ModuleItemLikeBinding {
                return ModuleItemLikeBinding.inflate(inflater, parent, false)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ModuleActivityLikeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        setupRefreshAndLoadMore(
            binding.swipeRefreshLayout,
            viewModel,
            adapter,
            binding.recyclerView,
            this
        )

    }

    override val emptyLayoutId: Int
        get() = R.layout.module_layout_empty_list
}

