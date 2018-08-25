package com.wafflestudio.snuev.view.signin

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.squareup.moshi.Moshi
import com.wafflestudio.snuev.model.meta.AuthTokenMeta
import com.wafflestudio.snuev.model.resource.User
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.preference.SnuevPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moe.banana.jsonapi2.Document

class SignInViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    val username = ObservableField<String>()
    val password = ObservableField<String>()

    fun signIn() {
        val username = username.get() ?: return
        val password = password.get() ?: return
        disposables.add(SnuevApi.service.signIn(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onSignInRequest() }
                .doOnTerminate { onSignInFinish() }
                .subscribe(
                        { response -> onSignInSuccess(response) },
                        { onSignInFailure() }
                ))
    }

    private fun fetchUser() {
        disposables.add(SnuevApi.service.fetchUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchUserRequest() }
                .doOnTerminate { onFetchUserFinish() }
                .subscribe(
                        { response -> onFetchUserSuccess(response) },
                        { onFetchUserFailure() }
                ))
    }

    private fun onSignInRequest() {
    }

    private fun onSignInFinish() {
    }

    private fun onSignInSuccess(response: Document) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(AuthTokenMeta::class.java)
        val authTokenMeta = response.meta.get<AuthTokenMeta>(jsonAdapter) as AuthTokenMeta
        SnuevPreference.token = authTokenMeta.auth_token
        fetchUser()
    }

    private fun onSignInFailure() {
    }

    private fun onFetchUserRequest() {
    }

    private fun onFetchUserFinish() {
    }

    private fun onFetchUserSuccess(user: User) {
    }

    private fun onFetchUserFailure() {
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}