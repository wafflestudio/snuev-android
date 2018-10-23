package com.wafflestudio.snuev.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    NetworkModule::class,
    PreferenceModule::class,
    ViewModelModule::class
])
interface AppComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<DaggerApplication>()
}