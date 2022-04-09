package com.ke.zhihu.module.ui.newfollow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.ke.mvvm.base.ui.BaseViewBindingAdapter
import com.ke.mvvm.base.ui.IBaseRefreshAndLoadMoreView
import com.ke.zhihu.api.response.InvitationResponse
import com.ke.zhihu.module.R
import com.ke.zhihu.module.databinding.ModuleActivityNewFollowBinding
import com.ke.zhihu.module.databinding.ModuleItemNewFollowBinding
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint

@Route(path = PagePath.PAGE_NEW_FOLLOW)
@AndroidEntryPoint
class NewFollowActivity : AppCompatActivity(), IBaseRefreshAndLoadMoreView {
    private val viewModel: NewFollowViewModel by viewModels()
    private lateinit var binding: ModuleActivityNewFollowBinding

    private val adapter =
        object : BaseViewBindingAdapter<InvitationResponse, ModuleItemNewFollowBinding>() {
            override fun bindItem(
                item: InvitationResponse,
                viewBinding: ModuleItemNewFollowBinding,
                viewType: Int,
                position: Int
            ) {
                val header = item.emptyInfo["text"]

                if (header != null) {
                    viewBinding.group.isVisible = false
                    viewBinding.header.isVisible = true
                    viewBinding.header.text = header
                } else {
                    viewBinding.group.isVisible = true
                    viewBinding.header.isVisible = false
                    Glide.with(this@NewFollowActivity)
                        .load(item.head.avatarUrls.firstOrNull())
                        .into(viewBinding.avatar)
                    viewBinding.name.text = item.content.title
                    viewBinding.headline.text = item.date
                }
            }

            override fun createViewBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
                viewType: Int
            ): ModuleItemNewFollowBinding {
                return ModuleItemNewFollowBinding.inflate(inflater, parent, false)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ModuleActivityNewFollowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

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