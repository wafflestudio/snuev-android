package com.wafflestudio.snuev.network

import com.squareup.moshi.Moshi
import com.wafflestudio.snuev.model.resource.Department
import com.wafflestudio.snuev.model.resource.User
import com.wafflestudio.snuev.preference.SnuevPreference
import moe.banana.jsonapi2.JsonApiConverterFactory
import moe.banana.jsonapi2.ResourceAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object SnuevApi {
    val service by lazy {
        val jsonAdapterFactory = ResourceAdapterFactory.builder()
                .add(Department::class.java)
                .add(User::class.java)
                .build()
        val moshi = Moshi.Builder()
                .add(jsonAdapterFactory)
                .build()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addNetworkInterceptor { chain ->
                    val builder = chain.request().newBuilder()
                    SnuevPreference.token?.let { token ->
                        builder.addHeader("Authorization", token)
                    } ?: let {
                    }
                    chain.proceed(builder.build())
                }
                .build()
        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.snuev.kr")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JsonApiConverterFactory.create(moshi))
                .build()
        retrofit.create(SnuevEndpoint::class.java)
    }
}