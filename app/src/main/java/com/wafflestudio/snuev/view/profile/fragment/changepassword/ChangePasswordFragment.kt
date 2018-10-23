package com.wafflestudio.snuev.view.profile.fragment.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.view.base.BaseFragment

class ChangePasswordFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_change_password, container, false)

    companion object {
        fun newInstance(): ChangePasswordFragment = ChangePasswordFragment()
    }
}
