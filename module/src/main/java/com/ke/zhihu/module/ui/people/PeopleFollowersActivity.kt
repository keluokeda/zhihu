package com.ke.zhihu.module.ui.people

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.ke.zhihu.module.utils.PagePath
import dagger.hilt.android.AndroidEntryPoint

@Route(path = PagePath.PAGE_PEOPLE_FOLLOWERS)
@AndroidEntryPoint
class PeopleFollowersActivity : BasePeopleListActivity() {
    override val basePeopleListViewModel: PeopleFollowersViewModel by viewModels()


}