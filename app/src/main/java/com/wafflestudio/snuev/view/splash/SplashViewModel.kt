package com.wafflestudio.snuev.view.splash

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wafflestudio.snuev.model.resource.User
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.preference.SnuevPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.HttpURLConnection

class SplashViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    val user: MutableLiveData<User> = MutableLiveData()
    val unauthorized: MutableLiveData<Boolean> = MutableLiveData()
    val errorFetchUser: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchUser() {
        disposables.add(SnuevApi.service.fetchUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchUserRequest() }
                .doOnTerminate { onFetchUserFinish() }
                .subscribe(
                        { onFetchUserSuccess(it) },
                        { onFetchUserFailure(it) }
                ))
    }

    private fun onFetchUserRequest() {}

    private fun onFetchUserFinish() {}

    private fun onFetchUserSuccess(response: User) {
        SnuevPreference.user = response
        user.value = response
    }

    private fun onFetchUserFailure(error: Throwable) {
        if (error is HttpException && error.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            unauthorized.value = true
        } else {
            errorFetchUser.value = true
        }
    }
}