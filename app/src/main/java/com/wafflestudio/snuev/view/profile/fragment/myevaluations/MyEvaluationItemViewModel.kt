package com.wafflestudio.snuev.view.profile.fragment.myevaluations

import android.arch.lifecycle.LifecycleOwner
import android.databinding.ObservableField
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.view.base.BaseItemViewModel
import com.wafflestudio.snuev.viewmodel.VoteViewModel
import io.reactivex.disposables.CompositeDisposable

class MyEvaluationItemViewModel(
        lifecycleOwner: LifecycleOwner,
        override val api: SnuevApi
) :
        BaseItemViewModel(lifecycleOwner),
        VoteViewModel {
    override val disposables: CompositeDisposable = CompositeDisposable()

    override val upVoted = ObservableField(false)
    override val downVoted = ObservableField(false)

    override val upVoteCount = ObservableField(0)
    override val downVoteCount = ObservableField(0)

    override val upVoting = ObservableField(false)
    override val downVoting = ObservableField(false)
}