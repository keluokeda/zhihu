package com.ke.zhihu.module.domain

import com.ke.mvvm.base.domian.UseCase
import com.ke.zhihu.module.db.Account
import com.ke.zhihu.module.db.AccountDao
import com.ke.zhihu.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class InsertAccountUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val accountDao: AccountDao
) : UseCase<Account, Boolean>(dispatcher) {

    override suspend fun execute(parameters: Account): Boolean {

        val current = accountDao.getCurrent()

        if (current == null) {
            parameters.current = true
        } else {
            if (parameters.current) {
                current.current = false
                accountDao.insert(current)
            }
        }
        accountDao.insert(parameters)

        return true

    }
}