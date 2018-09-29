package com.wafflestudio.snuev.view.detail

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableField
import com.wafflestudio.snuev.model.resource.Evaluation
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.view.base.BaseItemViewModel
import com.wafflestudio.snuev.viewmodel.BookmarkViewModel
import com.wafflestudio.snuev.viewmodel.VoteViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

// Detail Evaluation Item ViewModel
class DetailEvaluationItemViewModel(lifecycleOwner: LifecycleOwner) :
        BaseItemViewModel(lifecycleOwner),
        VoteViewModel {
    override val disposables = CompositeDisposable()

    override val upVoted = ObservableField(false)
    override val downVoted = ObservableField(false)

    override val upVoteCount = ObservableField(0)
    override val downVoteCount = ObservableField(0)

    override val upVoting = ObservableField(false)
    override val downVoting = ObservableField(false)

    var evaluation: Evaluation? = null
        set(value) {
            value?.let {
                upVoted.set(it.upVoted)
                downVoted.set(it.downVoted)
                upVoteCount.set(it.upVotesCount)
                downVoteCount.set(it.downVotesCount)
            }
        }
}