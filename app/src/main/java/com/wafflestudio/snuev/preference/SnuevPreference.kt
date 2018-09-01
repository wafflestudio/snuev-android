package com.wafflestudio.snuev.preference

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.wafflestudio.snuev.model.resource.Department
import com.wafflestudio.snuev.model.resource.User
import com.wafflestudio.snuev.util.PREFERENCE
import com.wafflestudio.snuev.util.PREFERENCE_AUTH_TOKEN_KEY
import com.wafflestudio.snuev.util.PREFERENCE_USER_KEY
import moe.banana.jsonapi2.ResourceAdapterFactory

object SnuevPreference {
    private var sharedPreferences: SharedPreferences? = null
    private var moshi: Moshi? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        }
        val jsonAdapterFactory = ResourceAdapterFactory.builder()
                .add(Department::class.java)
                .add(User::class.java)
                .build()
        moshi = Moshi.Builder()
                .add(jsonAdapterFactory)
                .build()
    }

    var token: String?
        get() = readString(PREFERENCE_AUTH_TOKEN_KEY)
        set(token) {
            token?.let { value ->
                writeString(PREFERENCE_AUTH_TOKEN_KEY, value)
            }
        }

    var user: User?
        get() {
            val moshi = moshi ?: return null
            val encoded = readString(PREFERENCE_USER_KEY) ?: return null
            return moshi.adapter(User::class.java).fromJson(encoded)
        }
        set(value) {
            val moshi = moshi ?: return
            value?.let {
                writeString(PREFERENCE_USER_KEY, moshi.adapter(User::class.java).toJson(it))
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