package com.wafflestudio.snuev.view.main

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.model.resource.Lecture
import com.wafflestudio.snuev.view.base.BaseAdapter
import com.wafflestudio.snuev.view.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_main_lecture.view.*

class MainLectureAdapter(
        owner: LifecycleOwner,
        items: MutableLiveData<List<Lecture>>
) : BaseAdapter<Lecture, MainLectureAdapter.LectureHolder>(owner, items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_main_lecture, parent, false)
        return LectureHolder(view)
    }

    class LectureHolder(view: View) : BaseViewHolder<Lecture>(view) {
        private lateinit var lecture: Lecture

        override fun bind(data: Lecture, position: Int) {
            lecture = data
            view.text_lecture_evaluation_count.text = lecture.evaluationsCount.toString()
            view.text_lecture_name.text = lecture.name
            view.text_lecture_detail.text = context.getString(
                    R.string.lecture_details,
                    lecture.getCourse()?.getDepartment()?.name ?: "",
                    lecture.getCourse()?.targetGrade ?: "",
                    lecture.getProfessor()?.name ?: ""
            )
        }
    }
}