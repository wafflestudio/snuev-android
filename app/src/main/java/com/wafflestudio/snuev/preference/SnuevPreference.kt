package com.wafflestudio.snuev.preference

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.wafflestudio.snuev.model.resource.Department
import com.wafflestudio.snuev.model.resource.User
import com.wafflestudio.snuev.util.PREFERENCE
import com.wafflestudio.snuev.util.PREFERENCE_AUTH_TOKEN_KEY
import com.wafflestudio.snuev.util.PREFERENCE_USER_KEY
import moe.banana.jsonapi2.Document
import moe.banana.jsonapi2.ResourceAdapterFactory
import javax.inject.Inject

class SnuevPreference(
        private val sharedPreferences: SharedPreferences,
        private val moshi: Moshi
) {
    var token: String?
        get() = readString(PREFERENCE_AUTH_TOKEN_KEY)
        set(token) {
            token?.let { value ->
                writeString(PREFERENCE_AUTH_TOKEN_KEY, value)
            }
        }

    var user: User?
        get() {
            val encoded = readString(PREFERENCE_USER_KEY) ?: return null
            val document = moshi.adapter(Document::class.java).fromJson(encoded)
            return document?.asObjectDocument<User>()?.get()
        }
        set(value) {
            value?.let {
                writeString(PREFERENCE_USER_KEY, moshi.adapter(Document::class.java).toJson(it.document))
            }
        }

    fun clear() {
        val edit = sharedPreferences.edit()
        edit.clear()
        edit.apply()
    }

    private fun readString(key: String): String? = sharedPreferences.getString(key, null)

    private fun writeString(key: String, value: String, callback: (() -> Unit)? = null) {
        val edit = sharedPreferences.edit()
        edit.putString(key, value)
        callback?.let {
            edit.apply { callback() }
        } ?: let {
            edit.commit()
        }
    }
}