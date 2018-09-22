package com.wafflestudio.snuev.view.base

import android.support.v7.app.AppCompatActivity
import com.wafflestudio.snuev.SnuevApplication

abstract class BaseActivity : AppCompatActivity() {
    val application: SnuevApplication
    get() = super.getApplication() as SnuevApplication
}
