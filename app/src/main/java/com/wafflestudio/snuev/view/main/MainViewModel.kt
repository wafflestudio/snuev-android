package com.wafflestudio.snuev.view.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wafflestudio.snuev.model.resource.Evaluation
import com.wafflestudio.snuev.model.resource.Lecture
import com.wafflestudio.snuev.network.SnuevApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    val latestEvaluations: MutableLiveData<List<Evaluation>> = MutableLiveData()
    val mostEvaluatedLectures: MutableLiveData<List<Lecture>> = MutableLiveData()
    val topRatedLectures: MutableLiveData<List<Lecture>> = MutableLiveData()
    val mostLikedEvaluations: MutableLiveData<List<Evaluation>> = MutableLiveData()

//    fetch lectures and evaluations
    fun fetchLatestEvaluations() {
        val disposable = SnuevApi.service.fetchLatestEvaluations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchLatestEvaluationsRequest() }
                .doOnTerminate { onFetchLatestEvaluationsFinish() }
                .subscribe(
                        { onFetchLatestEvaluationsSuccess(it) },
                        { onFetchLatestEvaluationsFailure(it) }
                )
        disposables.add(disposable)
    }

    fun fetchMostEvaluatedLectures() {
        val disposable = SnuevApi.service.fetchMostEvaluatedLectures()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchMostEvaluatedLecturesRequest() }
                .doOnTerminate { onFetchMostEvaluatedLecturesFinish() }
                .subscribe(
                        { onFetchMostEvaluatedLecturesSuccess(it) },
                        { onFetchMostEvaluatedLecturesFailure(it) }
                )
        disposables.add(disposable)
    }

    fun fetchTopRatedLectures() {
        val disposable = SnuevApi.service.fetchTopRatedLectures()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchTopRatedLecturesRequest() }
                .doOnTerminate { onFetchTopRatedLecturesFinish() }
                .subscribe(
                        { onFetchTopRatedLecturesSuccess(it) },
                        { onFetchTopRatedLecturesFailure(it) }
                )
        disposables.add(disposable)
    }

    fun fetchMostLikedEvaluations() {
         val disposable = SnuevApi.service.fetchMostLikedEvaluations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchMostLikedEvaluationsRequest() }
                .doOnTerminate { onFetchMostLikedEvaluationsFinish() }
                .subscribe(
                        { onFetchMostLikedEvaluationsSuccess(it) },
                        { onFetchMostLikedEvaluationsFailure(it) }
                )
        disposables.add(disposable)
    }

//    Latest Evaluations
    private fun onFetchLatestEvaluationsRequest() {}
    private fun onFetchLatestEvaluationsFinish() {}
    private fun onFetchLatestEvaluationsSuccess(response: List<Evaluation>) {
        latestEvaluations.value = response.take(3)
    }
    private fun onFetchLatestEvaluationsFailure(error: Throwable) {
        error.printStackTrace()
    }

//    Most Evaluated Lectures
    private fun onFetchMostEvaluatedLecturesRequest() {}
    private fun onFetchMostEvaluatedLecturesFinish() {}
    private fun onFetchMostEvaluatedLecturesSuccess(response: List<Lecture>) {
        mostEvaluatedLectures.value = response.take(3)
    }
    private fun onFetchMostEvaluatedLecturesFailure(error: Throwable) {
        error.printStackTrace()
    }

//    Top Rated Lectures
    private fun onFetchTopRatedLecturesRequest() {}
    private fun onFetchTopRatedLecturesFinish() {}
    private fun onFetchTopRatedLecturesSuccess(response: List<Lecture>) {
        topRatedLectures.value = response.take(3)
    }
    private fun onFetchTopRatedLecturesFailure(error: Throwable) {
        error.printStackTrace()
    }

//    Most Liked Evaluations
    private fun onFetchMostLikedEvaluationsRequest() {}
    private fun onFetchMostLikedEvaluationsFinish() {}
    private fun onFetchMostLikedEvaluationsSuccess(response: List<Evaluation>) {
        mostLikedEvaluations.value = response.take(3)
    }
    private fun onFetchMostLikedEvaluationsFailure(error: Throwable) {
        error.printStackTrace()
    }

//    Clear
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}