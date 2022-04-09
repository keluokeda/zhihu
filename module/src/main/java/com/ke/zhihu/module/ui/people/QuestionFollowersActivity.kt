package com.ke.zhihu.module.ui.people

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.MaterialToolbar
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint

@Route(path = PagePath.PAGE_QUESTION_FOLLOWERS)
@AndroidEntryPoint
class QuestionFollowersActivity : BasePeopleListActivity() {
    override val basePeopleListViewModel: QuestionFollowersViewModel by viewModels()


    override fun initToolbar(materialToolbar: MaterialToolbar) {
        super.initToolbar(materialToolbar)
        materialToolbar.title = "关注的人"
    }
}