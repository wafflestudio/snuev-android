package com.wafflestudio.snuev.view.detail

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.model.resource.Evaluation
import com.wafflestudio.snuev.view.base.BaseAdapter
import com.wafflestudio.snuev.view.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_detail_evaluation.view.*

class DetailEvaluationAdapter(
        owner: LifecycleOwner,
        items: MutableLiveData<List<Evaluation>>
) : BaseAdapter<Evaluation, DetailEvaluationAdapter.EvaluationHolder>(owner, items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvaluationHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_detail_evaluation, parent, false)
        return EvaluationHolder(view)
    }

    class EvaluationHolder(view: View) : BaseViewHolder<Evaluation>(view) {
        private lateinit var evaluation: Evaluation

        override fun bind(data: Evaluation, position: Int) {
            evaluation = data
            view.text_score.text = context.getString(R.string.score_0_decimals, evaluation.score)
            view.text_easiness.text = context.getString(R.string.score_0_decimals, evaluation.easiness)
            view.text_grading.text = context.getString(R.string.score_0_decimals, evaluation.grading)
            view.text_evaluation.text = evaluation.comment
            view.text_date.text = evaluation.createdAt
            view.text_upvote_count.text = evaluation.upvotesCount.toString()
            view.text_downvote_count.text = evaluation.downvotesCount.toString()
        }
    }
}