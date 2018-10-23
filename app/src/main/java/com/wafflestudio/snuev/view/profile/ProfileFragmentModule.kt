package com.wafflestudio.snuev.view.profile

import com.wafflestudio.snuev.view.profile.fragment.about.AboutFragment
import com.wafflestudio.snuev.view.profile.fragment.bookmarkedlectures.BookmarkedLecturesFragment
import com.wafflestudio.snuev.view.profile.fragment.changepassword.ChangePasswordFragment
import com.wafflestudio.snuev.view.profile.fragment.myevaluations.MyEvaluationsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeAboutFragmentInjector(): AboutFragment

    @ContributesAndroidInjector
    abstract fun contributeBookmarkedLecturesFragmentInjector(): BookmarkedLecturesFragment

    @ContributesAndroidInjector
    abstract fun contributeChangePasswordFragmentInjector(): ChangePasswordFragment

    @ContributesAndroidInjector
    abstract fun contributeMyEvaluationsFragmentInjector(): MyEvaluationsFragment
}