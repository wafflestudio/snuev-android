package com.wafflestudio.snuev.extension

import com.squareup.moshi.Moshi
import moe.banana.jsonapi2.Document
import moe.banana.jsonapi2.ResourceAdapterFactory
import retrofit2.HttpException

fun Throwable.document(): Document? {
    val jsonAdapterFactory = ResourceAdapterFactory.builder().build()
    val moshi = Moshi.Builder().add(jsonAdapterFactory).build()
    return (this as? HttpException)?.response()?.errorBody()?.string()?.let { errorString ->
        moshi.adapter(Document::class.java).fromJson(errorString)
    }
}

fun Throwable.errorTitles(): List<String> {
    return this.document()?.let { document ->
        document.errors.map { error ->
            error.title
        }
    } ?: let {
        listOf<String>()
    }
}