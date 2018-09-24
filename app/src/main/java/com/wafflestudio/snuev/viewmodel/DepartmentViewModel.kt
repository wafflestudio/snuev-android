package com.wafflestudio.snuev.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import com.wafflestudio.snuev.model.resource.Department
import com.wafflestudio.snuev.network.SnuevApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

interface DepartmentViewModel {
    val disposables: CompositeDisposable

    val departments: MutableLiveData<List<Department>>
    val departmentSearchQuery: ObservableField<String>
    val departmentSearchResult: MutableLiveData<List<Department>>

    fun searchDepartments() {
        val searchQuery = departmentSearchQuery.get() ?: return
        val departments = departments.value ?: return
        departmentSearchResult.value = departments
                .filter { department -> department.name.contains(searchQuery) }
                .take(3)
    }

    fun selectDepartment(department: Department)

    fun fetchDepartments() {
        val disposable = SnuevApi.service.fetchDepartments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchDepartmentsRequest() }
                .doOnTerminate { onFetchDepartmentsFinish() }
                .subscribe(
                        { onFetchDepartmentsSuccess(it) },
                        { onFetchDepartmentFailure(it) }
                )
        disposables.add(disposable)
    }

    private fun onFetchDepartmentsRequest() {}
    private fun onFetchDepartmentsFinish() {}
    private fun onFetchDepartmentsSuccess(response: List<Department>) {
        departments.value = response
        departmentSearchResult.value = response.take(3)
    }

    private fun onFetchDepartmentFailure(error: Throwable) {
        error.printStackTrace()
    }
}