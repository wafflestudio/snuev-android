package com.wafflestudio.snuev.view.profile

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.view.profile.fragment.AboutFragment
import com.wafflestudio.snuev.view.profile.fragment.ChangePasswordFragment
import com.wafflestudio.snuev.view.profile.fragment.EvaluationsFragment

class ProfilePagerAdapter(val context: Context, fragementManager: FragmentManager) : FragmentPagerAdapter(fragementManager) {
    private val pages = arrayOf(
            Pair({ AboutFragment.newInstance() }, R.string.tab_about),
            Pair({ ChangePasswordFragment.newInstance() }, R.string.tab_change_password),
            Pair({ EvaluationsFragment.newInstance() }, R.string.tab_evaluations)
    )

    override fun getItem(position: Int): Fragment? = pages[position].first.invoke()

    override fun getPageTitle(position: Int): CharSequence = context.getString(pages[position].second)

    override fun getCount(): Int = pages.size
}