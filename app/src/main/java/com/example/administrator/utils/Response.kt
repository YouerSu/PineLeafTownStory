package com.example.administrator.utils

/**
 * 实现此类,定期检查条件
 */

abstract class Response<T> : Thread() {
    abstract fun doThings(answer:T)
}
