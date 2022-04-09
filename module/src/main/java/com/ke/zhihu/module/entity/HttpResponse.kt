package com.ke.zhihu.module.entity

sealed interface HttpResponse<out T> {
    data class Success<out T>(val data: T) : HttpResponse<T>

    data class Error(
        val statusCode: Int = -1,
        val code: Int = -1,
        val name: String = "未知错误",
        val message: String = "未知错误"
    ) : HttpResponse<Nothing>
}

fun <T> T.toHttpResponseSuccess(): HttpResponse.Success<T> {
    return HttpResponse.Success(this)
}