package com.wafflestudio.snuev.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.wafflestudio.snuev.view.detail.DetailViewModel
import com.wafflestudio.snuev.view.evaluate.EvaluateViewModel
import com.wafflestudio.snuev.view.main.MainViewModel
import com.wafflestudio.snuev.view.profile.fragment.bookmarkedlectures.BookmarkedLecturesViewModel
import com.wafflestudio.snuev.view.profile.fragment.myevaluations.MyEvaluationItemViewModel
import com.wafflestudio.snuev.view.profile.fragment.myevaluations.MyEvaluationsViewModel
import com.wafflestudio.snuev.view.search.SearchViewModel
import com.wafflestudio.snuev.view.signin.SignInViewModel
import com.wafflestudio.snuev.view.signup.SignUpViewModel
import com.wafflestudio.snuev.view.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(signInViewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EvaluateViewModel::class)
    abstract fun bindEvaluateViewModel(evaluateViewModel: EvaluateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(signUpViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookmarkedLecturesViewModel::class)
    abstract fun bindBookmarkedLecturesViewModel(bookmarkedLecturesViewModel: BookmarkedLecturesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyEvaluationsViewModel::class)
    abstract fun bindMyEvaluationsViewModel(myEvaluationViewModel: MyEvaluationsViewModel): ViewModel
}