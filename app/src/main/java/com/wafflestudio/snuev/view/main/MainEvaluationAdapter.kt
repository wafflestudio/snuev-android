package com.wafflestudio.snuev.view.main

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.model.resource.Evaluation
import com.wafflestudio.snuev.model.resource.toString
import com.wafflestudio.snuev.view.base.BaseAdapter
import com.wafflestudio.snuev.view.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_main_evaluation.view.*

class MainEvaluationAdapter(
        owner: LifecycleOwner,
        items: MutableLiveData<List<Evaluation>>,
        private val onItemClick: (Evaluation) -> Unit
) : BaseAdapter<Evaluation, MainEvaluationAdapter.EvaluationHolder>(owner, items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvaluationHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_main_evaluation, parent, false)
        return EvaluationHolder(view, this, onItemClick)
    }

    var itemsClickable = true

    class EvaluationHolder(
            view: View,
            val adapter: MainEvaluationAdapter,
            private val onItemClick: (Evaluation) -> Unit
    ) : BaseViewHolder<Evaluation>(view) {
        private lateinit var evaluation: Evaluation

        override fun bind(data: Evaluation, position: Int) {
            evaluation = data
            view.text_lecture_name.text = evaluation.getLecture()?.name ?: ""
            view.text_semester.text = evaluation.getSemester()?.toString(context) ?: ""
            view.text_lecture_detail.text = context.getString(
                    R.string.lecture_details,
                    evaluation.getLecture()?.getCourse()?.getDepartment()?.name ?: "",
                    evaluation.getLecture()?.getCourse()?.targetGrade ?: "",
                    evaluation.getLecture()?.getProfessor()?.name ?: ""
            )
            view.text_score.text = context.getString(R.string.score_0_decimals, evaluation.score)
            view.text_easiness.text = context.getString(R.string.score_0_decimals, evaluation.easiness)
            view.text_grading.text = context.getString(R.string.score_0_decimals, evaluation.grading)
            view.text_evaluation.text = evaluation.comment
            view.text_date.text = evaluation.createdAt

            view.clicks().subscribe {
                if (adapter.itemsClickable) {
                    onItemClick(data)
                }
            }
        }
    }
}