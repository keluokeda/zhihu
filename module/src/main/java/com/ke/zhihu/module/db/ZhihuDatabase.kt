package com.ke.zhihu.module.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Account::class],
    version = 1
)
abstract class ZhihuDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
}