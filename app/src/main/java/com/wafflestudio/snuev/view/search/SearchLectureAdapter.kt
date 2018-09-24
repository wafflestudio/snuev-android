package com.wafflestudio.snuev.view.search

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.model.resource.Lecture
import com.wafflestudio.snuev.view.base.BaseAdapter
import com.wafflestudio.snuev.view.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_search_lecture.view.*

class SearchLectureAdapter(
        owner: LifecycleOwner,
        items: MutableLiveData<List<Lecture>>,
        private val onItemClick: (Lecture) -> Unit
) : BaseAdapter<Lecture, SearchLectureAdapter.LectureHolder>(owner, items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_search_lecture, parent, false)
        return LectureHolder(view, this, onItemClick)
    }

    var itemsClickable = true

    class LectureHolder(
            view: View,
            val adapter: SearchLectureAdapter,
            private val onItemClick: (Lecture) -> Unit
    ) : BaseViewHolder<Lecture>(view) {
        private lateinit var lecture: Lecture

        override fun bind(data: Lecture, position: Int) {
            lecture = data
            view.text_lecture_name.text = lecture.name
            view.text_lecture_detail.text = context.getString(
                    R.string.lecture_details,
                    lecture.getCourse()?.getDepartment()?.name ?: "",
                    lecture.getCourse()?.targetGrade ?: "",
                    lecture.getProfessor()?.name ?: ""
            )
            view.text_lecture_view_count.text = lecture.viewCount.toString()
            view.text_lecture_evaluation_count.text = lecture.evaluationsCount.toString()
            view.text_lecture_score.text = context.getString(R.string.score_1_decimals, lecture.score)

            view.clicks().subscribe {
                if (adapter.itemsClickable) {
                    onItemClick(data)
                }
            }
        }
    }
}