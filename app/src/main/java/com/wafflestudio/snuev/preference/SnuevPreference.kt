package com.wafflestudio.snuev.preference

import android.content.Context
import android.content.SharedPreferences
import com.wafflestudio.snuev.util.PREFERENCE
import com.wafflestudio.snuev.util.PREFERENCE_AUTH_TOKEN_KEY

object SnuevPreference {
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        }
    }

    var token: String?
    get() = readString(PREFERENCE_AUTH_TOKEN_KEY)
    set(token) {
        token?.let { value ->
            writeString(PREFERENCE_AUTH_TOKEN_KEY, value)
        }
    }

    private fun readString(key: String): String? {
        val sharedPreferences = sharedPreferences ?: return null
        return sharedPreferences.getString(key, null)
    }

    private fun writeString(key: String, value: String, callback: (() -> Unit)? = null) {
        val sharedPreferences = sharedPreferences ?: return
        val edit = sharedPreferences.edit()
        edit.putString(key, value)
        callback?.let {
            edit.apply { callback() }
        } ?: let {
            edit.commit()
        }
    }
}