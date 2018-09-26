package com.wafflestudio.snuev.view.signup

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.squareup.moshi.Moshi
import com.wafflestudio.snuev.extension.errorTitles
import com.wafflestudio.snuev.model.meta.AuthTokenMeta
import com.wafflestudio.snuev.model.request.SignUpRequest
import com.wafflestudio.snuev.model.resource.Department
import com.wafflestudio.snuev.model.resource.User
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.preference.SnuevPreference
import com.wafflestudio.snuev.viewmodel.DepartmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moe.banana.jsonapi2.Document

class SignUpViewModel : ViewModel(), DepartmentViewModel {
    override val disposables = CompositeDisposable()

    init {
        fetchDepartments()
    }

    val username = ObservableField<String>()
    val nickname = ObservableField<String>()
    val password = ObservableField<String>()

    // errors
    val usernameFieldEmpty = MutableLiveData<Boolean>()
    val accountExists = MutableLiveData<Boolean>()
    val departmentFieldEmpty = MutableLiveData<Boolean>()
    val nicknameFieldEmpty = MutableLiveData<Boolean>()
    val passwordFieldEmpty = MutableLiveData<Boolean>()
    val passwordTooShort = MutableLiveData<Boolean>()

    fun clearUsernameError() {
        usernameFieldEmpty.value = false
        accountExists.value = false
    }

    fun clearDepartmentError() {
        departmentFieldEmpty.value = false
    }

    fun clearNicknameError() {
        nicknameFieldEmpty.value = false
    }

    fun clearPasswordError() {
        passwordFieldEmpty.value = false
        passwordTooShort.value = false
    }

    val isFetching = ObservableField<Boolean>(false)

    val user: MutableLiveData<User> = MutableLiveData()

    override val departmentSearchQuery = ObservableField<String>()
    private val selectedDepartment = ObservableField<Department>()
    override val departments: MutableLiveData<List<Department>> = MutableLiveData()
    override val departmentSearchResult: MutableLiveData<List<Department>> = MutableLiveData()

    override fun selectDepartment(department: Department) {
        departmentSearchQuery.set(department.name)
        selectedDepartment.set(department)
    }

    fun signUp() {
        var isValid = true

        val username = username.get()
        val departmentId = selectedDepartment.get()?.id
        val nickname = nickname.get()
        val password = password.get()

        if (username.isNullOrBlank()) {
            isValid = false
            usernameFieldEmpty.value = true
        }

        if (departmentId.isNullOrBlank()) {
            isValid = false
            departmentFieldEmpty.value = true
        }

        if (nickname.isNullOrBlank()) {
            isValid = false
            nicknameFieldEmpty.value = true
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
        departmentId ?: return
        nickname ?: return
        password ?: return

        disposables.add(SnuevApi.service.signUp(SignUpRequest(
                username = username,
                departmentId = departmentId,
                nickname = nickname,
                password = password
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onSignUpRequest() }
                .subscribe(
                        { onSignUpSuccess(it) },
                        { onSignUpFailure(it) }
                )
        )
    }

    private fun fetchUser() {
        disposables.add(SnuevApi.service.fetchUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { onFetchUserFinish() }
                .subscribe { onFetchUserSuccess(it) }
        )
    }

    private fun onSignUpRequest() {
        isFetching.set(true)
    }

    private fun onSignUpSuccess(response: Document) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(AuthTokenMeta::class.java)
        val authTokenMeta = response.meta.get<AuthTokenMeta>(jsonAdapter) as AuthTokenMeta
        SnuevPreference.token = authTokenMeta.auth_token
        fetchUser()
    }

    private fun onSignUpFailure(error: Throwable) {
        isFetching.set(false)
        if (error.errorTitles().contains("User already exists")) {
            accountExists.value = true
        }
    }

    private fun onFetchUserFinish() {
        isFetching.set(false)
    }
    private fun onFetchUserSuccess(response: User) {
        SnuevPreference.user = response
        user.value = response
    }

    private fun onFetchUserFailure(error: Throwable) {
        error.printStackTrace()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}