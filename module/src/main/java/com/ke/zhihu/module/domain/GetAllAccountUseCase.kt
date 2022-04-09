package com.ke.zhihu.module.domain

import com.ke.mvvm.base.domian.UseCase
import com.ke.zhihu.module.db.Account
import com.ke.zhihu.module.db.AccountDao
import com.ke.zhihu.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetAllAccountUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val accountDao: AccountDao
) : UseCase<Unit, List<Account>>(dispatcher) {

    override suspend fun execute(parameters: Unit): List<Account> {
        return accountDao.getAll()
    }
}