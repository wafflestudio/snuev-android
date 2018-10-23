package com.wafflestudio.snuev.di

import dagger.Binds
import dagger.Module
import dagger.android.DaggerApplication

@Module
abstract class AppModule {
    @Binds
    abstract fun application(application: DaggerApplication): DaggerApplication
}