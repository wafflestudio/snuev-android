package com.wafflestudio.snuev.view.profile.fragment.bookmarkedlectures

import android.arch.lifecycle.MutableLiveData
import android.databinding.DataBindingUtil
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.databinding.ItemBookmarkedLectureBinding
import com.wafflestudio.snuev.model.resource.Lecture
import com.wafflestudio.snuev.view.base.BaseAdapter
import com.wafflestudio.snuev.view.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_main_evaluation.view.*

class BookmarkedLectureAdapter(
        private val fragment: Fragment,
        items: MutableLiveData<List<Lecture>>,
        private val onItemClick: (Lecture) -> Unit
) : BaseAdapter<Lecture, BookmarkedLectureAdapter.LectureHolder>(fragment, items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_bookmarked_lecture, parent, false)
        return LectureHolder(view, fragment, this, onItemClick)
    }

    var itemsClickable = true

    class LectureHolder(
            view: View,
            fragment: Fragment,
            private val adapter: BookmarkedLectureAdapter,
            private val onItemClick: (Lecture) -> Unit
    ) : BaseViewHolder<Lecture>(view) {
        private lateinit var lecture: Lecture
        private val viewModel = BookmarkedLectureItemViewModel(fragment)
        private val binding by lazy { DataBindingUtil.bind<ItemBookmarkedLectureBinding>(view) }

        override fun bind(data: Lecture, position: Int) {
            lecture = data
            binding?.viewmodel = viewModel
            binding?.lecture = lecture

            view.text_lecture_detail.text = context.getString(
                    R.string.lecture_details,
                    lecture.getCourse()?.getDepartment()?.name ?: "",
                    lecture.getCourse()?.targetGrade ?: "",
                    lecture.getProfessor()?.name ?: ""
            )

            view.clicks().subscribe {
                if (adapter.itemsClickable) {
                    onItemClick(data)
                }
            }
        }
    }
}