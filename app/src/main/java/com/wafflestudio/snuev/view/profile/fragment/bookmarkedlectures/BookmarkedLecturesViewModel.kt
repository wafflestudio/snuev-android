package com.wafflestudio.snuev.view.profile.fragment.bookmarkedlectures

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.wafflestudio.snuev.model.resource.Lecture
import com.wafflestudio.snuev.network.SnuevApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BookmarkedLecturesViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    val bookmarkedLectures = MutableLiveData<List<Lecture>>()
    val fetchingBookmarkedLectures = ObservableField<Boolean>()

    init {
        fetchBookmarkedLectures()
    }

    private fun fetchBookmarkedLectures() {
        disposables.add(SnuevApi.service.fetchBookmarked()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchBookmarkedLecturesRequest() }
                .doOnTerminate { onFetchBookmarkedLecturesFinish() }
                .subscribe { onFetchBookmarkedLectures(it) }
        )
    }

    private fun onFetchBookmarkedLecturesRequest() {
        fetchingBookmarkedLectures.set(true)
    }

    private fun onFetchBookmarkedLecturesFinish() {
        fetchingBookmarkedLectures.set(false)
    }

    private fun onFetchBookmarkedLectures(response: List<Lecture>) {
        bookmarkedLectures.value = response
    }
}