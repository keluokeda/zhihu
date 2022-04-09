package com.ke.zhihu.module.ui.invitationanswer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.ke.zhihu.module.R
import com.ke.zhihu.module.databinding.ModuleActivityInvitationAnswerBinding
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint

@Route(path = PagePath.PAGE_INVITATION_ANSWER)
@AndroidEntryPoint
class InvitationAnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ModuleActivityInvitationAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val fragmentList = listOf(
            InvitationFragment(),
            RecommendFragment(),
        )
        val titleList = listOf("邀请", "推荐")
        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragmentList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }

        }
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, index ->
            tab.text = titleList[index]
        }.attach()

    }
}