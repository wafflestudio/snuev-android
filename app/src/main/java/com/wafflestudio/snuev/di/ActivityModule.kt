package com.wafflestudio.snuev.di

import com.wafflestudio.snuev.view.detail.DetailActivity
import com.wafflestudio.snuev.view.evaluate.EvaluateActivity
import com.wafflestudio.snuev.view.main.MainActivity
import com.wafflestudio.snuev.view.profile.ProfileActivity
import com.wafflestudio.snuev.view.profile.ProfileFragmentModule
import com.wafflestudio.snuev.view.search.SearchActivity
import com.wafflestudio.snuev.view.signin.SignInActivity
import com.wafflestudio.snuev.view.signup.SignUpActivity
import com.wafflestudio.snuev.view.signup.SignUpCompleteActivity
import com.wafflestudio.snuev.view.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashActivityInjector(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeSignInActivityInjector(): SignInActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchActivityInjector(): SearchActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivityInjector(): DetailActivity

    @ContributesAndroidInjector
    abstract fun contributeEvaluateActivityInjector(): EvaluateActivity

    @ContributesAndroidInjector(modules = [ProfileFragmentModule::class])
    abstract fun contributeProfileActivityInjector(): ProfileActivity

    @ContributesAndroidInjector
    abstract fun contributeSignUpActivityInjector(): SignUpActivity

    @ContributesAndroidInjector
    abstract fun contributeSignUpCompleteActivityInjector(): SignUpCompleteActivity
}