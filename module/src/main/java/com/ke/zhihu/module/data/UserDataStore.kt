package com.ke.zhihu.module.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataStore @Inject constructor(private val keyValueStore: KeyValueStore) {

    var authorization: String?
        get() = keyValueStore.getString(KEY_AUTHORIZATION, null)
        set(value) = keyValueStore.saveString(KEY_AUTHORIZATION, value)

    var loginUserId: String?
        get() = keyValueStore.getString(KEY_USER_ID, null)
        set(value) = keyValueStore.saveString(KEY_USER_ID, value)

    companion object {
        private const val KEY_AUTHORIZATION = "KEY_AUTHORIZATION"
        private const val KEY_USER_ID = "KEY_USER_ID"
    }
}