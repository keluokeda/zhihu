package com.ke.zhihu.module.db

import androidx.room.*

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account)

    @Query("select * from accounts")
    suspend fun getAll(): List<Account>

    @Delete
    suspend fun delete(account: Account)

    /**
     * 获取当前用户
     */
    @Query("select * from accounts where current = 1")
    suspend fun getCurrent(): Account?
}