package com.wafflestudio.snuev.view.profile

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.view.profile.fragment.about.AboutFragment
import com.wafflestudio.snuev.view.profile.fragment.bookmarkedlectures.BookmarkedLecturesFragment
import com.wafflestudio.snuev.view.profile.fragment.changepassword.ChangePasswordFragment
import com.wafflestudio.snuev.view.profile.fragment.myevaluations.MyEvaluationsFragment

class ProfilePagerAdapter(val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val pages = arrayOf(
            Pair({ AboutFragment.newInstance() }, R.string.tab_about),
            Pair({ MyEvaluationsFragment.newInstance() }, R.string.tab_my_evaluations),
            Pair({ BookmarkedLecturesFragment.newInstance() }, R.string.tab_bookmarked_lectures),
            Pair({ ChangePasswordFragment.newInstance() }, R.string.tab_change_password)
    )

    override fun getItem(position: Int): Fragment? = pages[position].first.invoke()

    override fun getPageTitle(position: Int): CharSequence = context.getString(pages[position].second)

    override fun getCount(): Int = pages.size
}