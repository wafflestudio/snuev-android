package com.wafflestudio.snuev.view.profile.fragment.myevaluations

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.wafflestudio.snuev.model.resource.Evaluation
import com.wafflestudio.snuev.network.SnuevApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MyEvaluationsViewModel @Inject constructor(private val api: SnuevApi) : ViewModel() {
    val disposables = CompositeDisposable()

    val myEvaluations = MutableLiveData<List<Evaluation>>()
    val fetchingMyEvaluations = ObservableField<Boolean>()

    init {
        fetchMyEvaluations()
    }

    private fun fetchMyEvaluations() {
        disposables.add(api.fetchMyEvaluations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchMyEvaluationsRequest() }
                .doOnTerminate { onFetchMyEvaluationsFinish() }
                .subscribe { onFetchMyEvaluationsSuccess(it) }
        )
    }

    private fun onFetchMyEvaluationsRequest() {
        fetchingMyEvaluations.set(true)
    }

    private fun onFetchMyEvaluationsFinish() {
        fetchingMyEvaluations.set(false)
    }

    private fun onFetchMyEvaluationsSuccess(response: List<Evaluation>) {
        myEvaluations.value = response
    }
}