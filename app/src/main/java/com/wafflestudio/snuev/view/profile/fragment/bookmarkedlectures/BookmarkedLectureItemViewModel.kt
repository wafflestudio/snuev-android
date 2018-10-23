package com.wafflestudio.snuev.view.profile.fragment.bookmarkedlectures

import android.arch.lifecycle.LifecycleOwner
import android.databinding.ObservableField
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.view.base.BaseItemViewModel
import com.wafflestudio.snuev.viewmodel.BookmarkViewModel
import io.reactivex.disposables.CompositeDisposable

class BookmarkedLectureItemViewModel(
        lifecycleOwner: LifecycleOwner,
        override val api: SnuevApi
) : BaseItemViewModel(lifecycleOwner),
        BookmarkViewModel {
    override val disposables = CompositeDisposable()
    override val bookmarked = ObservableField<Boolean>(true)
    override val isBookmarking = ObservableField<Boolean>(false)
}