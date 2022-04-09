package com.ke.zhihu.module.ui.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.parseAsHtml
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.ke.mvvm.base.ui.BaseViewBindingAdapter
import com.ke.mvvm.base.ui.IBaseRefreshAndLoadMoreView
import com.ke.mvvm.base.ui.launchAndRepeatWithViewLifecycle
import com.ke.zhihu.api.response.CommentResponse
import com.ke.zhihu.module.R
import com.ke.zhihu.module.databinding.ModuleFoolterMoreCommentsBinding
import com.ke.zhihu.module.databinding.ModuleItemChildCommentBinding
import com.ke.zhihu.module.databinding.ModuleLayoutToolbarRefreshListBinding
import com.ke.zhihu.module.entity.CommentSortType
import com.ke.zhihu.module.toDateString
import com.ke.zhihu.module.utils.ExtraKey
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@Route(path = PagePath.PAGE_COMMENTS)
@AndroidEntryPoint
class CommentsActivity : AppCompatActivity(), IBaseRefreshAndLoadMoreView {

    private lateinit var binding: ModuleLayoutToolbarRefreshListBinding

    private val adapter = CommentAdapter(this) { item, viewBinding ->
        viewBinding.children.adapter = createChildrenAdapter(item)
    }
//        object : BaseViewBindingAdapter<CommentResponse, ModuleItemCommentBinding>() {
//            override fun bindItem(
//                item: CommentResponse,
//                viewBinding: ModuleItemCommentBinding,
//                viewType: Int,
//                position: Int
//            ) {
//
//                bindComment(item, viewBinding, this@CommentsActivity)
//                viewBinding.children.adapter = createChildrenAdapter(item)
//            }
//
//            override fun createViewBinding(
//                inflater: LayoutInflater,
//                parent: ViewGroup,
//                viewType: Int
//            ): ModuleItemCommentBinding {
//                return ModuleItemCommentBinding.inflate(inflater, parent, false)
//            }
//
//        }


    private fun createChildrenAdapter(commentResponse: CommentResponse): RecyclerView.Adapter<*>? {

        if (commentResponse.childCommentCount == 0) {
            return null
        }

        val adapter =
            object : BaseViewBindingAdapter<CommentResponse, ModuleItemChildCommentBinding>() {
                override fun bindItem(
                    item: CommentResponse,
                    viewBinding: ModuleItemChildCommentBinding,
                    viewType: Int,
                    position: Int
                ) {
                    Glide.with(this@CommentsActivity)
                        .load(item.author.avatarUrl)
                        .into(viewBinding.avatar)
                    viewBinding.name.text = item.author.name
                    viewBinding.target.isVisible = item.replyToAuthor != null
                    viewBinding.target.text = item.replyToAuthor?.name
                    viewBinding.content.text = item.content.parseAsHtml()
                    viewBinding.like.text =
                        if (item.likeCount > 0) item.likeCount.toString() else ""
                    viewBinding.date.text = item.createdTime.toDateString()
                }

                override fun createViewBinding(
                    inflater: LayoutInflater,
                    parent: ViewGroup,
                    viewType: Int
                ): ModuleItemChildCommentBinding {
                    return ModuleItemChildCommentBinding.inflate(inflater, parent, false)
                }

            }

        adapter.setList(commentResponse.childComments)

        if (commentResponse.childCommentCount > 2) {
            val footer = ModuleFoolterMoreCommentsBinding.inflate(layoutInflater)
            footer.viewAll.text = "查看全部${commentResponse.childCommentCount}评论"
            footer.viewAll.setOnClickListener {
                ARouter.getInstance().build(PagePath.PAGE_CHILD_COMMENTS)
                    .withSerializable(ExtraKey.COMMENT, commentResponse)
                    .navigation()
            }

            adapter.addFooterView(footer.root)
        }

        return adapter
    }

    private val commentsViewModel: CommentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ModuleLayoutToolbarRefreshListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.apply {
            setNavigationIcon(R.drawable.module_baseline_arrow_back_white_24dp)
            setNavigationOnClickListener {
                onBackPressed()
            }
            this.title = "全部评论"

            this.inflateMenu(R.menu.module_comment_sort)

            setOnMenuItemClickListener {
                val type = CommentSortType.values().find { type ->
                    type.menuId == it.itemId
                }!!

                commentsViewModel.setSortType(type)
                true
            }

        }

        setupRefreshAndLoadMore(
            binding.swipeRefreshLayout,
            commentsViewModel,
            adapter,
            binding.recyclerView,
            this
        )

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        launchAndRepeatWithViewLifecycle {
            commentsViewModel.sortType.collect {
                binding.toolbar.apply {
//                    binding.toolbar.menu.getItem(it.menuId).isChecked = true

                    binding.toolbar.menu.children.find { item ->
                        item.itemId == it.menuId
                    }?.isChecked = true

                }
            }
        }
    }


    override val emptyLayoutId: Int
        get() = R.layout.module_layout_empty_list
}
