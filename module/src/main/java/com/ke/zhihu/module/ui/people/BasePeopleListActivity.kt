package com.ke.zhihu.module.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.ke.mvvm.base.data.Result
import com.ke.mvvm.base.data.successOr
import com.ke.mvvm.base.ui.BaseViewBindingAdapter
import com.ke.mvvm.base.ui.IBaseRefreshAndLoadMoreView
import com.ke.zhihu.api.response.PeopleResponse
import com.ke.zhihu.module.R
import com.ke.zhihu.module.data.UserDataStore
import com.ke.zhihu.module.databinding.ModuleActivityBasePeopleListBinding
import com.ke.zhihu.module.databinding.ModuleItemPeopleBinding
import com.ke.zhihu.module.domain.CancelFollowPeopleUseCase
import com.ke.zhihu.module.domain.FollowPeopleUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
abstract class BasePeopleListActivity : AppCompatActivity(), IBaseRefreshAndLoadMoreView {

    @Inject
    lateinit var userDataStore: UserDataStore

    @Inject
    lateinit var followPeopleUseCase: FollowPeopleUseCase

    @Inject
    lateinit var cancelFollowPeopleUseCase: CancelFollowPeopleUseCase

    abstract val basePeopleListViewModel: BasePeopleListViewModel<*>
    override val emptyLayoutId: Int
        get() = R.layout.module_layout_empty_list

    protected val adapter =
        object : BaseViewBindingAdapter<PeopleResponse, ModuleItemPeopleBinding>() {
            override fun bindItem(
                item: PeopleResponse,
                viewBinding: ModuleItemPeopleBinding,
                viewType: Int,
                position: Int
            ) {
                this@BasePeopleListActivity.bindItem(item, viewBinding, viewType, position)

            }

            override fun createViewBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
                viewType: Int
            ): ModuleItemPeopleBinding {
                return ModuleItemPeopleBinding.inflate(inflater, parent, false)
            }

        }

    protected open fun bindItem(
        item: PeopleResponse,
        viewBinding: ModuleItemPeopleBinding,
        viewType: Int,
        position: Int
    ) {
        Glide.with(this@BasePeopleListActivity)
            .load(item.avatarUrl)
            .into(viewBinding.avatar)
        viewBinding.name.text = item.name
        viewBinding.headline.text = item.headline

        viewBinding.follow.isVisible = item.id != userDataStore.loginUserId

        viewBinding.follow.text = if (item.isFollowing) "已关注" else "关注"

        viewBinding.follow.setOnClickListener {
            lifecycleScope.launch {
                val useCase =
                    if (item.isFollowing) cancelFollowPeopleUseCase else followPeopleUseCase

//                when (val result = useCase(item.id)) {
//                    is Result.Success -> {
//
//                    }
//                    is Result.Error -> {
//                        result.exception.printStackTrace()
//                    }
//                }

                item.isFollowing = useCase(item.id).successOr(false)
                adapter.notifyItemChanged(position)
            }
        }
    }

    private lateinit var binding: ModuleActivityBasePeopleListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ModuleActivityBasePeopleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar(binding.toolbar)

        setupRefreshAndLoadMore(
            binding.swipeRefreshLayout,
            basePeopleListViewModel,
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

    @CallSuper
    protected open fun initToolbar(materialToolbar: MaterialToolbar) {
        materialToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}