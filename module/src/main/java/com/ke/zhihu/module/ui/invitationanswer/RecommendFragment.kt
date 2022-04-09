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
import com.ke.zhihu.api.response.InvitationAnswerResponse
import com.ke.zhihu.module.R
import com.ke.zhihu.module.databinding.ModuleItemInvitationAnswerRecommendBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class RecommendFragment : Fragment(), IBaseRefreshAndLoadMoreView {

    private val binding: KeMvvmLayoutBaseRefreshListRetryBinding by viewbind()

    private val viewModel: RecommendViewModel by viewModels()

    private val adapter = object :
        BaseViewBindingAdapter<InvitationAnswerResponse, ModuleItemInvitationAnswerRecommendBinding>() {
        override fun bindItem(
            item: InvitationAnswerResponse,
            viewBinding: ModuleItemInvitationAnswerRecommendBinding,
            viewType: Int,
            position: Int
        ) {
            viewBinding.apply {
                Glide.with(this@RecommendFragment)
                    .load(item.question.author.avatarUrl)
                    .into(avatar)
                reason.text = item.reason
                title.text = item.question.title
                footer.text = "${item.question.answerCount}个回答-${item.question.followerCount}个关注"
            }
        }

        override fun createViewBinding(
            inflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ): ModuleItemInvitationAnswerRecommendBinding {

            return ModuleItemInvitationAnswerRecommendBinding.inflate(inflater, parent, false)
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