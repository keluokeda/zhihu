package com.ke.zhihu.module.ui.message

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.hi.dhl.binding.viewbind
import com.ke.mvvm.base.ui.BaseViewBindingAdapter
import com.ke.mvvm.base.ui.IBaseRefreshAndLoadMoreView
import com.ke.mvvm.base.ui.collectSnackbarFlow
import com.ke.zhihu.api.response.InvitationResponse
import com.ke.zhihu.module.R
import com.ke.zhihu.module.databinding.ModuleItemMessageBinding
import com.ke.zhihu.module.databinding.ModuleLayoutToolbarRefreshListBinding
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageListFragment : Fragment(), IBaseRefreshAndLoadMoreView {

    override val emptyLayoutId: Int
        get() = R.layout.module_layout_empty_list

    private val binding: ModuleLayoutToolbarRefreshListBinding by viewbind()

    private val viewModel: MessageListViewModel by viewModels()

    private val adapter =
        object : BaseViewBindingAdapter<InvitationResponse, ModuleItemMessageBinding>() {
            override fun bindItem(
                item: InvitationResponse,
                viewBinding: ModuleItemMessageBinding,
                viewType: Int,
                position: Int
            ) {
                Glide.with(this@MessageListFragment)
                    .load(item.head.avatarUrls.firstOrNull())
                    .into(viewBinding.avatar)
                viewBinding.name.text = item.content.title
                viewBinding.content.text = item.content.text
                viewBinding.date.text = item.date
                viewBinding.isTop.isVisible = item.isTop
            }

            override fun createViewBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
                viewType: Int
            ): ModuleItemMessageBinding {
                return ModuleItemMessageBinding.inflate(inflater, parent, false)
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
            title = "消息"
            setNavigationIcon(R.drawable.module_baseline_arrow_back_white_24dp)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
            menu.let {
                it.add(0, 0, 0, "邀请回答").setIcon(R.drawable.module_baseline_person_add_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                it.add(0, 1, 0, "赞同与喜欢")
                    .setIcon(R.drawable.module_baseline_favorite_border_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                it.add(0, 2, 0, "赞同与喜欢").setIcon(R.drawable.module_outline_visibility_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                it.add(0, 3, 0, "全部已读")

                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        0 -> {
                            ARouter.getInstance().build(PagePath.PAGE_INVITATION_ANSWER)
                                .navigation()
                        }

                        1 -> {
                            ARouter.getInstance().build(PagePath.PAGE_LIKE_LIST).navigation()
                        }
                        2 -> {
                            ARouter.getInstance().build(PagePath.PAGE_NEW_FOLLOW).navigation()
                        }

                        3 -> {
                            viewModel.readAll()
//                            ARouter.getInstance().build(PagePath.PAGE_QUESTION)
//                                .navigation()
                        }

                        else -> {
                            throw IllegalArgumentException("错误的 item id ${item.itemId}")
                        }
                    }

                    true
                }
            }
        }

        setupRefreshAndLoadMore(
            binding.swipeRefreshLayout,
            viewModel,
            adapter,
            binding.recyclerView,
            viewLifecycleOwner
        )

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        collectSnackbarFlow(viewModel)
    }
}