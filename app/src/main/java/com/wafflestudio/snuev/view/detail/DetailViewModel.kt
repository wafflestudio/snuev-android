package com.wafflestudio.snuev.view.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wafflestudio.snuev.model.resource.Evaluation
import com.wafflestudio.snuev.model.resource.Lecture
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.util.default
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    val lectureName = MutableLiveData<String>().default("")
    val professorName = MutableLiveData<String>().default("")
    val score = MutableLiveData<Float>().default(0f)
    val easiness = MutableLiveData<Float>().default(0f)
    val grading = MutableLiveData<Float>().default(0f)
    val evaluations: MutableLiveData<List<Evaluation>> = MutableLiveData()

    fun fetchLecture(id: String) {
        disposables.add(SnuevApi.service.fetchLecture(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchLectureRequest() }
                .doOnTerminate { onFetchLectureFinish() }
                .subscribe(
                        { onFetchLectureSuccess(it) },
                        { onFetchLectureFailure(it) }
                ))
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
                ))
    }

    private fun onFetchLectureRequest() {}
    private fun onFetchLectureFinish() {}
    private fun onFetchLectureSuccess(response: Lecture) {
        lectureName.value = response.name
        professorName.value = response.getProfessor()?.name ?: ""
        score.value = response.score
        easiness.value = response.easiness
        grading.value = response.grading
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

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}