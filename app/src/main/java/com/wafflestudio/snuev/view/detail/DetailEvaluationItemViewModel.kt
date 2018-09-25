package com.wafflestudio.snuev.view.detail

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableField
import com.wafflestudio.snuev.model.resource.Evaluation
import com.wafflestudio.snuev.network.SnuevApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

// Detail Evaluation Item ViewModel
class DetailEvaluationItemViewModel(lifecycleOwner: LifecycleOwner) {
    init {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onClear() {
                disposables.dispose()
                lifecycleOwner.lifecycle.removeObserver(this)
            }
        })
    }

    private val disposables = CompositeDisposable()

    val upVoted = ObservableField(false)
    val downVoted = ObservableField(false)

    val upVoteCount = ObservableField(0)
    val downVoteCount = ObservableField(0)

    val upVoting = ObservableField(false)
    val downVoting = ObservableField(false)

    var evaluation: Evaluation? = null
        set(value) {
            value?.let {
                upVoted.set(it.upVoted)
                downVoted.set(it.downVoted)
                upVoteCount.set(it.upVotesCount)
                downVoteCount.set(it.downVotesCount)
            }
        }

    fun vote(lectureId: String, evaluationId: String, isUpVote: Boolean) {
        disposables.add(SnuevApi.service.vote(lectureId, evaluationId, isUpVote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onVoteRequest(isUpVote) }
                .doOnTerminate { onVoteFinish(isUpVote) }
                .subscribe({}) { onVoteFailure(isUpVote, it) }
        )
    }

    private fun onVoteRequest(isUpVote: Boolean) {
        if (isUpVote) {
            upVoting.set(true)
            upVoteCount.get()?.let { count ->
                upVoteCount.set(if (upVoted.get() == true) {
                    count - 1
                } else {
                    if (downVoted.get() == true) {
                        downVoteCount.get()?.let { downVoteCount.set(it - 1) }
                        downVoted.set(false)
                    }
                    count + 1
                })
            }
            upVoted.set(upVoted.get() == false)
        } else {
            downVoting.set(true)
            downVoteCount.get()?.let { count ->
                downVoteCount.set(if (downVoted.get() == true) {
                    count - 1
                } else {
                    if (upVoted.get() == true) {
                        upVoteCount.get()?.let { upVoteCount.set(it - 1) }
                        upVoted.set(false)
                    }
                    count + 1
                })
            }
            downVoted.set(downVoted.get() == false)
        }
    }

    private fun onVoteFinish(isUpVote: Boolean) {
        if (isUpVote) {
            upVoting.set(false)
        } else {
            downVoting.set(false)
        }
    }

    private fun onVoteFailure(isUpVote: Boolean, error: Throwable) {
        if (isUpVote) {
            upVoteCount.get()?.let { count ->
                upVoteCount.set(if (upVoted.get() == true) {
                    count - 1
                } else {
                    count + 1
                })
            }
            upVoted.set(upVoted.get() == false)
        } else {
            downVoteCount.get()?.let { count ->
                downVoteCount.set(if (downVoted.get() == true) {
                    count - 1
                } else {
                    count + 1
                })
            }
            downVoted.set(downVoted.get() == false)
        }
        error.printStackTrace()
    }
}