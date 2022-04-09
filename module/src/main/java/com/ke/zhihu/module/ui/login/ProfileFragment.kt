package com.ke.zhihu.module.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.hi.dhl.binding.viewbind
import com.ke.zhihu.module.databinding.ModuleFragmentProfileBinding
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ProfileFragment : Fragment() {

    private val binding: ModuleFragmentProfileBinding by viewbind()
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }


        Glide.with(this)
            .load(loginViewModel.currentUser.avatarUrl)
            .into(binding.avatar)

        binding.name.text = loginViewModel.currentUser.name
        binding.headline.text = loginViewModel.currentUser.headline

        binding.confirm.setOnClickListener {
            loginViewModel.saveUserId()
            ARouter.getInstance().build(PagePath.PAGE_MAIN)
                .navigation()
            activity?.finish()
        }
    }


}