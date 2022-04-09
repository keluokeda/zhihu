package com.ke.zhihu.module.ui.people

import com.ke.mvvm.base.domian.GetDataListUseCase
import com.ke.mvvm.base.ui.BaseRefreshAndLoadMoreViewModel
import com.ke.zhihu.api.response.PeopleResponse

abstract class BasePeopleListViewModel<P>(getDataListUseCase: GetDataListUseCase<P, PeopleResponse>) :
    BaseRefreshAndLoadMoreViewModel<P, PeopleResponse>(getDataListUseCase) {


}