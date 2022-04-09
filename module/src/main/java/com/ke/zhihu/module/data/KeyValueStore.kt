package com.ke.zhihu.module.data

import android.content.Context
import com.tencent.mmkv.MMKV
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface KeyValueStore {


    fun saveString(key: String, value: String?)

    fun getString(key: String, default: String?): String?

    fun saveInt(key: String, value: Int)

    fun getInt(key: String, default: Int): Int

    fun saveBool(key: String, value: Boolean)

    fun getBool(key: String, default: Boolean): Boolean
}

class MMKVKeyValueStore @Inject constructor(
    @ApplicationContext private val context: Context
) :
    KeyValueStore {

    init {
        MMKV.initialize(context)
    }


    override fun saveString(key: String, value: String?) {
        MMKV.defaultMMKV().encode(key, value)
    }

    override fun getString(key: String, default: String?): String? {
        return MMKV.defaultMMKV().getString(key, default)
    }

    override fun saveInt(key: String, value: Int) {
        MMKV.defaultMMKV().encode(key, value)
    }

    override fun getInt(key: String, default: Int): Int {
        return MMKV.defaultMMKV().getInt(key, default)
    }

    override fun saveBool(key: String, value: Boolean) {
        MMKV.defaultMMKV().encode(key, value)
    }

    override fun getBool(key: String, default: Boolean): Boolean {
        return MMKV.defaultMMKV().getBoolean(key, default)
    }
}