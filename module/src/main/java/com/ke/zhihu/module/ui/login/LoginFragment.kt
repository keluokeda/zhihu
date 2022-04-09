package com.ke.zhihu.module.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hi.dhl.binding.viewbind
import com.ke.zhihu.module.databinding.ModuleFragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class LoginFragment : Fragment() {

    private val binding: ModuleFragmentLoginBinding by viewbind()
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

        binding.login.setOnClickListener {
            loginViewModel.login(
                binding.authorization.text?.toString() ?: ""
            )
        }
    }

}