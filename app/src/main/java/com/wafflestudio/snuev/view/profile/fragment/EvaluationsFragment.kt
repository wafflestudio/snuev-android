package com.wafflestudio.snuev.view.profile.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafflestudio.snuev.R

class EvaluationsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_evaluations, container, false)

    companion object {
        fun newInstance(): EvaluationsFragment = EvaluationsFragment()
    }
}
