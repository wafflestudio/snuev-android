package com.wafflestudio.snuev.view.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wafflestudio.snuev.model.resource.Course
import com.wafflestudio.snuev.model.resource.Lecture
import com.wafflestudio.snuev.network.SnuevApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {
    private val disposable = CompositeDisposable()

    var searchResultLectures: MutableLiveData<List<Lecture>> = MutableLiveData()

    fun searchLectures(query: String) {
        val disposable = SnuevApi.service.searchLectures(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onSearchLecturesRequest() }
                .doOnTerminate { onSearchLecturesFinish() }
                .subscribe(
                        { onSearchLecturesSuccess(it) },
                        { onSearchLecturesFailure(it) }
                )
    }

    fun onSearchLecturesRequest() {}
    fun onSearchLecturesFinish() {}
    fun onSearchLecturesSuccess(response: List<Lecture>) {
        searchResultLectures.value = response
    }

    fun onSearchLecturesFailure(error: Throwable) {
        error.printStackTrace()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}