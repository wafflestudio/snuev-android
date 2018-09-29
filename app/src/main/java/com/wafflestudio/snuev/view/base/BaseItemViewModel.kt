package com.wafflestudio.snuev.view.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseItemViewModel(lifecycleOwner: LifecycleOwner) {
    init {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onClear() {
                disposables.dispose()
                lifecycleOwner.lifecycle.removeObserver(this)
            }
        })
    }

    abstract val disposables: CompositeDisposable
}