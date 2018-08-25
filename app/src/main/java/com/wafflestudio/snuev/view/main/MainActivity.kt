package com.wafflestudio.snuev.view.main

import android.os.Bundle
import android.os.PersistableBundle
import com.wafflestudio.snuev.R
import com.wafflestudio.snuev.view.base.BaseActivity

class MainActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
    }
}
