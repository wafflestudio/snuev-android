package com.wafflestudio.snuev.view.signin

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.squareup.moshi.Moshi
import com.wafflestudio.snuev.extension.errorTitles
import com.wafflestudio.snuev.model.meta.AuthTokenMeta
import com.wafflestudio.snuev.model.resource.User
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.preference.SnuevPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moe.banana.jsonapi2.Document
import javax.inject.Inject

class SignInViewModel @Inject constructor(
        private val api: SnuevApi,
        private val preference: SnuevPreference
) : ViewModel() {
    private val disposables = CompositeDisposable()

    val username = ObservableField<String>()
    val password = ObservableField<String>()

    // errors
    val usernameFieldEmpty = MutableLiveData<Boolean>()
    val passwordFieldEmpty = MutableLiveData<Boolean>()
    val passwordTooShort = MutableLiveData<Boolean>()
    val invalidCredentials = MutableLiveData<Boolean>()

    fun clearUsernameError() {
        usernameFieldEmpty.value = false
        if (invalidCredentials.value == true) {
            invalidCredentials.value = false
        }
    }

    fun clearPasswordError() {
        passwordFieldEmpty.value = false
        passwordTooShort.value = false
        invalidCredentials.value = false
    }

    val isFetching = ObservableField<Boolean>(false)

    val user: MutableLiveData<User> = MutableLiveData()

    fun signIn() {
        var isValid = true

        val username = username.get()
        val password = password.get()

        if (username.isNullOrBlank()) {
            isValid = false
            usernameFieldEmpty.value = true
        }

        if (password.isNullOrBlank()) {
            isValid = false
            passwordFieldEmpty.value = true
        } else if (password != null && password.length < 8) {
            isValid = false
            passwordTooShort.value = true
        }

        if (!isValid) {
            return
        }

        username ?: return
        password ?: return

        disposables.add(api.signIn(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onSignInRequest() }
                .subscribe(
                        { onSignInSuccess(it) },
                        { onSignInFailure(it) }
                )
        )
    }

    private fun fetchUser() {
        disposables.add(api.fetchUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { onFetchUserFinish() }
                .subscribe { onFetchUserSuccess(it) }
        )
    }

    private fun onSignInRequest() {
        isFetching.set(true)
    }

    private fun onSignInSuccess(response: Document) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(AuthTokenMeta::class.java)
        val authTokenMeta = response.meta.get<AuthTokenMeta>(jsonAdapter) as AuthTokenMeta
        preference.token = authTokenMeta.auth_token
        fetchUser()
    }

    private fun onSignInFailure(error: Throwable) {
        isFetching.set(false)
        if (error.errorTitles().contains("Invalid credentials")) {
            invalidCredentials.value = true
        }
    }

    private fun onFetchUserFinish() {
        isFetching.set(false)
    }

    private fun onFetchUserSuccess(response: User) {
        preference.user = response
        user.value = response
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}