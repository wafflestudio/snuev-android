package com.wafflestudio.snuev.di

import com.squareup.moshi.Moshi
import com.wafflestudio.snuev.SnuevApplication
import com.wafflestudio.snuev.model.resource.*
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.preference.SnuevPreference
import com.wafflestudio.snuev.util.BASE_URL
import com.wafflestudio.snuev.util.HEADER_AUTH
import dagger.Module
import dagger.Provides
import dagger.android.DaggerApplication
import moe.banana.jsonapi2.JsonApiConverterFactory
import moe.banana.jsonapi2.ResourceAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import javax.net.ssl.HttpsURLConnection

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(
            application: DaggerApplication,
            logging: HttpLoggingInterceptor,
            preference: SnuevPreference
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .addNetworkInterceptor { chain ->
                    val builder = chain.request().newBuilder()
                    preference.token?.let { token ->
                        builder.addHeader(HEADER_AUTH, token)
                    }

                    val response = chain.proceed(builder.build())
                    if (response.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                        if (preference.token != null) {
                            (application as? SnuevApplication)?.signOut(preference)
                        }
                    }
                    response
                }
                .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingIntercepter(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideMoshi(jsonAdapterFactory: ResourceAdapterFactory): Moshi {
        return Moshi.Builder()
                .add(jsonAdapterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun provideJsonAdapterFactory(): ResourceAdapterFactory {
        return ResourceAdapterFactory.builder()
                .add(Course::class.java)
                .add(Department::class.java)
                .add(Evaluation::class.java)
                .add(Lecture::class.java)
                .add(Professor::class.java)
                .add(Semester::class.java)
                .add(User::class.java)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JsonApiConverterFactory.create(moshi))
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): SnuevApi {
        return retrofit.create(SnuevApi::class.java)
    }
}