package com.wafflestudio.snuev.util

import android.arch.lifecycle.MutableLiveData

fun <T : Any?>MutableLiveData<T>.default(initialValue: T) = apply { value = initialValue }