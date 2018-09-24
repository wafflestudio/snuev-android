package com.wafflestudio.snuev.view.signup

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.squareup.moshi.Moshi
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
        val username = username.get() ?: return
        val departmentId = selectedDepartment.get()?.id ?: return
        val nickname = nickname.get() ?: return
        val password = password.get() ?: return

        disposables.add(SnuevApi.service.signUp(SignUpRequest(
                username = username,
                departmentId = departmentId,
                nickname = nickname,
                password = password
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onSignUpRequest() }
                .doOnTerminate { onSignUpFinish() }
                .subscribe(
                        { onSignUpSuccess(it) },
                        { onSignUpFailure(it) }
                ))
    }

    private fun fetchUser() {
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

    private fun onSignUpRequest() {}
    private fun onSignUpFinish() {}
    private fun onSignUpSuccess(response: Document) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(AuthTokenMeta::class.java)
        val authTokenMeta = response.meta.get<AuthTokenMeta>(jsonAdapter) as AuthTokenMeta
        SnuevPreference.token = authTokenMeta.auth_token
        fetchUser()
    }

    private fun onSignUpFailure(error: Throwable) {
        error.printStackTrace()
    }

    private fun onFetchUserRequest() {}
    private fun onFetchUserFinish() {}
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