package com.wafflestudio.snuev.viewmodel

import android.databinding.ObservableField
import com.wafflestudio.snuev.network.SnuevApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moe.banana.jsonapi2.Document

interface BookmarkViewModel {
    val disposables: CompositeDisposable

    val api: SnuevApi
    val bookmarked: ObservableField<Boolean>
    val isBookmarking: ObservableField<Boolean>

    fun toggleBookmark(lectureId: String) {
        var bookmarkObservable: Observable<Document>? = null
        if (bookmarked.get() == true) {
            bookmarkObservable = api.unBookmark(lectureId)
        } else if (bookmarked.get() == false) {
            bookmarkObservable = api.bookmark(lectureId)
        }
        bookmarkObservable
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe { onBookmarkRequest() }
                ?.doOnTerminate { onBookmarkFinish() }
                ?.subscribe({}) { onBookmarkFailure() }
                ?.let { disposable ->
                    disposables.add(disposable)
                }
    }

    private fun onBookmarkRequest() {
        isBookmarking.set(true)
        bookmarked.set(bookmarked.get() != true)
    }

    private fun onBookmarkFailure() {
        bookmarked.set(bookmarked.get() != true)
    }

    private fun onBookmarkFinish() {
        isBookmarking.set(false)
    }
}