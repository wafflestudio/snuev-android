package com.wafflestudio.snuev.view.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.wafflestudio.snuev.model.resource.Evaluation
import com.wafflestudio.snuev.model.resource.Lecture
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.util.default
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moe.banana.jsonapi2.Document

class DetailViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    val lectureName = MutableLiveData<String>().default("")
    val professorName = MutableLiveData<String>().default("")
    val score = MutableLiveData<Float>().default(0f)
    val easiness = MutableLiveData<Float>().default(0f)
    val grading = MutableLiveData<Float>().default(0f)
    val evaluations: MutableLiveData<List<Evaluation>> = MutableLiveData()

    val bookmarked = ObservableField<Boolean>()
    val isBookmarking = ObservableField(false)

    fun fetchLecture(id: String) {
        disposables.add(SnuevApi.service.fetchLecture(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchLectureRequest() }
                .doOnTerminate { onFetchLectureFinish() }
                .subscribe(
                        { onFetchLectureSuccess(it) },
                        { onFetchLectureFailure(it) }
                )
        )
    }

    fun fetchLectureEvaluations(id: String, page: Int) {
        disposables.add(SnuevApi.service.fetchLectureEvaluations(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchLectureEvaluationsRequest() }
                .doOnTerminate { onFetchLectureEvaluationsFinish() }
                .subscribe(
                        { onFetchLectureEvaluationsSuccess(it) },
                        { onFetchLectureEvaluationsFailure(it) }
                )
        )
    }

    fun toggleBookmark(id: String) {
        var bookmarkObservable: Observable<Document>? = null
        if (bookmarked.get() == true) {
            bookmarkObservable = SnuevApi.service.unBookmark(id)
        } else if (bookmarked.get() == false) {
            bookmarkObservable = SnuevApi.service.bookmark(id)
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

    private fun onFetchLectureRequest() {}
    private fun onFetchLectureFinish() {}
    private fun onFetchLectureSuccess(response: Lecture) {
        lectureName.value = response.name
        professorName.value = response.getProfessor()?.name ?: ""
        score.value = response.score
        easiness.value = response.easiness
        grading.value = response.grading
        bookmarked.set(response.bookmarked)
    }

    private fun onFetchLectureFailure(error: Throwable) {
        error.printStackTrace()
    }

    private fun onFetchLectureEvaluationsRequest() {}
    private fun onFetchLectureEvaluationsFinish() {}
    private fun onFetchLectureEvaluationsSuccess(response: List<Evaluation>) {
        evaluations.value = response
    }

    private fun onFetchLectureEvaluationsFailure(error: Throwable) {
        error.printStackTrace()
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

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}