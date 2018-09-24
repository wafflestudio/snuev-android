package com.wafflestudio.snuev.view.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.model.resource.Department
import com.wafflestudio.snuev.model.resource.Filter
import com.wafflestudio.snuev.model.resource.Lecture
import com.wafflestudio.snuev.network.SnuevApi
import com.wafflestudio.snuev.util.default
import com.wafflestudio.snuev.viewmodel.DepartmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel(), DepartmentViewModel {
    override val disposables = CompositeDisposable()

    init {
        fetchDepartments()
    }

    override val departmentSearchQuery = ObservableField<String>()
    val selectedDepartments: MutableLiveData<List<Department>> = MutableLiveData()
    override val departments: MutableLiveData<List<Department>> = MutableLiveData()
    override val departmentSearchResult: MutableLiveData<List<Department>> = MutableLiveData()

    val gradeFilters = MutableLiveData<List<Filter>>().default(listOf(
            Filter(R.string.filter_grade_1), Filter(R.string.filter_grade_5),
            Filter(R.string.filter_grade_2), Filter(R.string.filter_grade_master),
            Filter(R.string.filter_grade_3), Filter(R.string.filter_grade_doctor),
            Filter(R.string.filter_grade_4), Filter(R.string.filter_grade_both)
    ))

    val creditFilters = MutableLiveData<List<Filter>>().default(listOf(
            Filter(R.string.filter_credit_1), Filter(R.string.filter_credit_3),
            Filter(R.string.filter_credit_2), Filter(R.string.filter_credit_4)
    ))

    val typeFilters = MutableLiveData<List<Filter>>().default(listOf(
            Filter(R.string.filter_type_cultural), Filter(R.string.filter_type_select),
            Filter(R.string.filter_type_paper), Filter(R.string.filter_type_major_select),
            Filter(R.string.filter_type_graduate), Filter(R.string.filter_type_major)
    ))

    val academicBasicFilters = MutableLiveData<List<Filter>>().default(listOf(
            Filter(R.string.filter_academic_basic_thought), Filter(R.string.filter_academic_basic_experimental),
            Filter(R.string.filter_academic_basic_foreign_language), Filter(R.string.filter_academic_basic_computer),
            Filter(R.string.filter_academic_basic_analysis)
    ))

    val academicWorldFilters = MutableLiveData<List<Filter>>().default(listOf(
            Filter(R.string.filter_academic_world_culture), Filter(R.string.filter_academic_world_social),
            Filter(R.string.filter_academic_world_art), Filter(R.string.filter_academic_world_nature),
            Filter(R.string.filter_academic_world_philosophy), Filter(R.string.filter_academic_world_life),
            Filter(R.string.filter_academic_world_politics_economics)
    ))

    val optionalCulturalStudiesFilters = MutableLiveData<List<Filter>>().default(listOf(
            Filter(R.string.filter_cultural_studies_pe), Filter(R.string.filter_cultural_studies_creativity),
            Filter(R.string.filter_cultural_studies_art), Filter(R.string.filter_cultural_studies_korea),
            Filter(R.string.filter_cultural_studies_leadership)
    ))

    var searchResultLectures: MutableLiveData<List<Lecture>> = MutableLiveData()

    fun searchLectures(query: String) {
        disposables.add(SnuevApi.service.searchLectures(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onSearchLecturesRequest() }
                .doOnTerminate { onSearchLecturesFinish() }
                .subscribe(
                        { onSearchLecturesSuccess(it) },
                        { onSearchLecturesFailure(it) }
                ))
    }

    override fun selectDepartment(department: Department) {
        departmentSearchQuery.set("")
        selectedDepartments.value = selectedDepartments.value?.filter {
            department.id != it.id
        }?.plus(department) ?: listOf(department)
    }

    fun removeDepartment(department: Department) {
        selectedDepartments.value = selectedDepartments.value?.filter {
            department.id != it.id
        }
    }

    private fun onSearchLecturesRequest() {}
    private fun onSearchLecturesFinish() {}
    private fun onSearchLecturesSuccess(response: List<Lecture>) {
        searchResultLectures.value = response
    }

    private fun onSearchLecturesFailure(error: Throwable) {
        error.printStackTrace()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}