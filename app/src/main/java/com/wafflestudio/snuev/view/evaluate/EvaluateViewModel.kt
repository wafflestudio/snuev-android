package com.wafflestudio.snuev.view.evaluate

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wafflestudio.snuev.model.resource.Evaluation
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.util.default
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EvaluateViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    val score = MutableLiveData<Int>().default(5)
    val easiness = MutableLiveData<Int>().default(5)
    val grading = MutableLiveData<Int>().default(5)
    val comment = MutableLiveData<String>().default("")

    val success = MutableLiveData<Boolean>().default(false)
    val isLoading = MutableLiveData<Boolean>().default(false)

    fun createEvaluation(lectureId: Int) {
        val evaluation = Evaluation()
        evaluation.score = (score.value ?: 0).toFloat()
        evaluation.easiness = (easiness.value ?: 0).toFloat()
        evaluation.grading = (grading.value ?: 0).toFloat()
        evaluation.comment = comment.value ?: ""
        val disposable = SnuevApi.service.createEvaluation(
                lectureId.toString(),
                evaluation
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onCreateEvaluationRequest() }
                .doOnTerminate { onCreateEvaluationFinish() }
                .subscribe(
                        { onCreateEvaluationSuccess() },
                        { onCreateEvaluationFailure(it) }
                )
        disposables.add(disposable)
    }

    private fun onCreateEvaluationRequest() {
        isLoading.value = true
    }
    private fun onCreateEvaluationFinish() {
        isLoading.value = false
    }
    private fun onCreateEvaluationSuccess() {
        success.value = true
    }
    private fun onCreateEvaluationFailure(error: Throwable) {
        error.printStackTrace()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}