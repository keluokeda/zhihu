package com.ke.zhihu.module.ui.invitationanswer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.hi.dhl.binding.viewbind
import com.ke.mvvm.base.databinding.KeMvvmLayoutBaseRefreshListRetryBinding
import com.ke.mvvm.base.ui.BaseViewBindingAdapter
import com.ke.mvvm.base.ui.IBaseRefreshAndLoadMoreView
import com.ke.zhihu.api.response.InvitationResponse
import com.ke.zhihu.module.R
import com.ke.zhihu.module.databinding.ModuleItemInvitationAnswerInvitationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class InvitationFragment : Fragment(), IBaseRefreshAndLoadMoreView {

    private val binding: KeMvvmLayoutBaseRefreshListRetryBinding by viewbind()

    private val viewModel: InvitationViewModel by viewModels()

    private val adapter = object :
        BaseViewBindingAdapter<InvitationResponse, ModuleItemInvitationAnswerInvitationBinding>() {
        override fun bindItem(
            item: InvitationResponse,
            viewBinding: ModuleItemInvitationAnswerInvitationBinding,
            viewType: Int,
            position: Int
        ) {

            Glide.with(this@InvitationFragment)
                .load(item.head.avatarUrls.firstOrNull())
                .into(viewBinding.avatar)

            viewBinding.name.text = item.content.title
            viewBinding.subTitle.text = item.content.subTitle
            viewBinding.title.text = item.targetSource.text
            viewBinding.footer.text = item.targetSource.subText
        }

        override fun createViewBinding(
            inflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ): ModuleItemInvitationAnswerInvitationBinding {

            return ModuleItemInvitationAnswerInvitationBinding.inflate(inflater, parent, false)
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

    }

    override val emptyLayoutId: Int
        get() = R.layout.module_layout_empty_list
}