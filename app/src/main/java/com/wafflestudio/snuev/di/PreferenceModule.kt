package com.wafflestudio.snuev.di

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.wafflestudio.snuev.preference.SnuevPreference
import com.wafflestudio.snuev.util.PREFERENCE
import dagger.Module
import dagger.Provides
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Module
class PreferenceModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(context: DaggerApplication): SharedPreferences { // Change to context
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSnuevPreference(sharedPreference: SharedPreferences, moshi: Moshi): SnuevPreference {
        return SnuevPreference(sharedPreference, moshi)
    }
}