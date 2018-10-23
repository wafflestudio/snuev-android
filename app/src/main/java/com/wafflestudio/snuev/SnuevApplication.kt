package com.wafflestudio.snuev

import android.content.Intent
import com.squareup.leakcanary.LeakCanary
import com.wafflestudio.snuev.di.DaggerAppComponent
import com.wafflestudio.snuev.preference.SnuevPreference
import com.wafflestudio.snuev.view.signin.SignInActivity
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class SnuevApplication : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        TimberLog.init()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().create(this)

    fun signOut(preference: SnuevPreference) {
        preference.clear()
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}