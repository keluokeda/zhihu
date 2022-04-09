package com.ke.zhihu.module.ui.people

import com.ke.zhihu.module.domain.GetPeopleFollowersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PeopleFollowersViewModel @Inject constructor(private val getPeopleFollowersUseCase: GetPeopleFollowersUseCase) :
    BasePeopleListViewModel<String>(getPeopleFollowersUseCase) {
    override val parameters: String
        get() = "f5a6888c26a9c7f05d87ffe45b2d0bed"

    override val startIndex: Int
        get() = 0

    init {
        loadData(true)
    }


}