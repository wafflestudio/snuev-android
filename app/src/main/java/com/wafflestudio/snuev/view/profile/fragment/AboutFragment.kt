package com.wafflestudio.snuev.view.profile.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.preference.SnuevPreference
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_about, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SnuevPreference.user?.let { user ->
            text_email.text = user.email
            text_nickname.text = user.nickname
            text_department.text = user.getDepartment()?.name ?: ""
        }
    }

    companion object {
        fun newInstance(): AboutFragment = AboutFragment()
    }
}