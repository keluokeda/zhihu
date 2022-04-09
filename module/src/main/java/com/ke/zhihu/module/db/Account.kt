package com.ke.zhihu.module.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey
    val id: String,

    val name: String,

    val headline: String,

    val avatar: String,

    val token: String,

    var current: Boolean
):Parcelable
