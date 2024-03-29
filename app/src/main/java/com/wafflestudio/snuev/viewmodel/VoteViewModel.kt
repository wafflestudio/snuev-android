package com.wafflestudio.snuev.viewmodel

import android.databinding.ObservableField
import com.wafflestudio.snuev.network.SnuevApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

interface VoteViewModel {
    val disposables: CompositeDisposable

    val api: SnuevApi
    val upVoted: ObservableField<Boolean>
    val downVoted: ObservableField<Boolean>

    val upVoteCount: ObservableField<Int>
    val downVoteCount: ObservableField<Int>

    val upVoting: ObservableField<Boolean>
    val downVoting: ObservableField<Boolean>

    fun vote(lectureId: String, evaluationId: String, isUpVote: Boolean) {
        disposables.add(api.vote(lectureId, evaluationId, isUpVote)
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
