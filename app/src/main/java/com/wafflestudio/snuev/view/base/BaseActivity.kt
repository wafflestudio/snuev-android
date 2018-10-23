package com.wafflestudio.snuev.view.base

import com.wafflestudio.snuev.SnuevApplication
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {
    val application: SnuevApplication
    get() = super.getApplication() as SnuevApplication
}
