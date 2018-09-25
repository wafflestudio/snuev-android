package com.wafflestudio.snuev.network

import com.squareup.moshi.Moshi
import com.wafflestudio.snuev.SnuevApplication
import com.wafflestudio.snuev.model.resource.*
import com.wafflestudio.snuev.preference.SnuevPreference
import com.wafflestudio.snuev.util.BASE_URL
import com.wafflestudio.snuev.util.HEADER_AUTH
import moe.banana.jsonapi2.JsonApiConverterFactory
import moe.banana.jsonapi2.ResourceAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.net.ssl.HttpsURLConnection

object SnuevApi {
    private var application: SnuevApplication? = null

    fun init(application: SnuevApplication) {
        this.application = application
    }

    val service: SnuevEndpoint by lazy {
        val jsonAdapterFactory = ResourceAdapterFactory.builder()
                .add(Course::class.java)
                .add(Department::class.java)
                .add(Evaluation::class.java)
                .add(Lecture::class.java)
                .add(Professor::class.java)
                .add(Semester::class.java)
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
                        builder.addHeader(HEADER_AUTH, token)
                    } ?: let {
                    }

                    val response = chain.proceed(builder.build())
                    if (response.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                        if (SnuevPreference.token != null) {
                            application?.signOut()
                        }
                    }
                    response
                }
                .build()
        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JsonApiConverterFactory.create(moshi))
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        retrofit.create(SnuevEndpoint::class.java)
    }
}