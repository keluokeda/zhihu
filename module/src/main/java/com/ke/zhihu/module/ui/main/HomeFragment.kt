package com.ke.zhihu.module.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.hi.dhl.binding.viewbind
import com.ke.mvvm.base.ui.BaseViewBindingAdapter
import com.ke.mvvm.base.ui.IBaseRefreshAndLoadMoreView
import com.ke.zhihu.api.response.HomeArticleResponse
import com.ke.zhihu.module.R
import com.ke.zhihu.module.databinding.ModuleFragmentHomeBinding
import com.ke.zhihu.module.databinding.ModuleItemHomeArticleBinding
import com.ke.zhihu.module.utils.ExtraKey
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), IBaseRefreshAndLoadMoreView {
    private val binding: ModuleFragmentHomeBinding by viewbind()
    private val homeViewModel: HomeViewModel by viewModels()

    private val adapter =
        object : BaseViewBindingAdapter<HomeArticleResponse, ModuleItemHomeArticleBinding>() {
            override fun bindItem(
                item: HomeArticleResponse,
                viewBinding: ModuleItemHomeArticleBinding,
                viewType: Int,
                position: Int
            ) {
                viewBinding.title.text = item.commonCard.feedContent.title.panelText
                viewBinding.content.text = item.commonCard.feedContent.content?.panelText
                viewBinding.image.isVisible = item.commonCard.feedContent.image != null

                item.commonCard.feedContent.image?.apply {
                    Glide.with(this@HomeFragment)
                        .load(imageUrl)
                        .into(viewBinding.image)
                }

                item.commonCard.feedContent.sourceLine.elements.also {
                    it.firstOrNull()?.avatar?.apply {
                        Glide.with(this@HomeFragment)
                            .load(image.url).into(viewBinding.avatar)
                    }

                    it.getOrNull(1)?.apply {
                        viewBinding.name.text = text?.panelText
                    }

                    it.getOrNull(2)?.apply {
                        viewBinding.authorDesc.text = text?.panelText
                    }
                }
                viewBinding.info.text = item.commonCard.footLine?.footerString


            }

            override fun createViewBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
                viewType: Int
            ): ModuleItemHomeArticleBinding {
                return ModuleItemHomeArticleBinding.inflate(inflater, parent, false)
            }

        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.apply {
            menu.add(0, 1, 0, "消息").setIcon(R.drawable.module_baseline_notifications_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

            setOnMenuItemClickListener {
                ARouter.getInstance().build(PagePath.PAGE_MESSAGE)
                    .navigation()
                true
            }
        }

        setupRefreshAndLoadMore(
            binding.swipeRefreshLayout,
            homeViewModel,
            adapter,
            binding.recyclerView,
            viewLifecycleOwner,
        )

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position)

            val id = item.extra["id"]
            val type = item.extra["type"]
//            val url =
            when (type) {
                "article" -> {
//                        "https://www.zhihu.com/appview/p/485480381?use_hybrid_toolbar=1&omni=1&is_enable_double_click_voteup=1&no_image=false&X-AD=canvas_version%3Av%3D5.1%3Bsetting%3Acad%3D0"
                }

                "answer" -> {
                    ARouter.getInstance().build(PagePath.PAGE_ANSWER)
                        .withString(ExtraKey.ID, id)
                        .navigation()
//                        "https://www.zhihu.com/appview/v2/answer/${id}"
                }
                else -> throw IllegalArgumentException("不支持的type $type")
            }

        }
    }

    override val emptyLayoutId: Int
        get() = R.layout.module_layout_empty_list
}