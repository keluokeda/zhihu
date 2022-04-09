package com.ke.zhihu.module.ui.comments

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.alibaba.android.arouter.facade.annotation.Route
import com.ke.mvvm.base.ui.IBaseRefreshAndLoadMoreView
import com.ke.zhihu.module.R
import com.ke.zhihu.module.databinding.ModuleHeaderChildCommentsBinding
import com.ke.zhihu.module.databinding.ModuleLayoutToolbarRefreshListBinding
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint

@Route(path = PagePath.PAGE_CHILD_COMMENTS)
@AndroidEntryPoint
class ChildCommentsActivity : AppCompatActivity(), IBaseRefreshAndLoadMoreView {

    private lateinit var binding: ModuleLayoutToolbarRefreshListBinding
    private val viewModel: ChildCommentsViewModel by viewModels()

    private val adapter by lazy {
        CommentAdapter(this) { _, _ -> }
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
            this.title = "${viewModel.commentResponse.childCommentCount}个回复"
        }

        val headerBinding = ModuleHeaderChildCommentsBinding.inflate(layoutInflater)
        bindComment(viewModel.commentResponse, headerBinding.parent, this)
        adapter.addHeaderView(headerBinding.root)

        setupRefreshAndLoadMore(
            binding.swipeRefreshLayout,
            viewModel,
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
    }

    override val emptyLayoutId: Int
        get() = R.layout.module_layout_empty_list
}


