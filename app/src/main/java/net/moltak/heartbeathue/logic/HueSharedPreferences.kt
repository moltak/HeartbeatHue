package net.moltak.heartbeathue.logic

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by engeng on 12/1/15.
 */
class HueSharedPreferences private constructor(appContext: Context) {
    private var mSharedPreferences: SharedPreferences? = null

    private var mSharedPreferencesEditor: SharedPreferences.Editor? = null

    init {
        mSharedPreferences = appContext.getSharedPreferences(HUE_SHARED_PREFERENCES_STORE, 0) // 0 - for private mode
        mSharedPreferencesEditor = mSharedPreferences!!.edit()
    }

    val username: String
        get() {
            val username = mSharedPreferences!!.getString(LAST_CONNECTED_USERNAME, "")
            return username
        }

    fun setUsername(username: String) {
        mSharedPreferencesEditor!!.putString(LAST_CONNECTED_USERNAME, username)
        mSharedPreferencesEditor!!.apply()
    }

    val lastConnectedIPAddress: String
        get() = mSharedPreferences!!.getString(LAST_CONNECTED_IP, "")

    fun setLastConnectedIPAddress(ipAddress: String) {
        mSharedPreferencesEditor!!.putString(LAST_CONNECTED_IP, ipAddress)
        mSharedPreferencesEditor!!.apply()
    }

    companion object {
        private val HUE_SHARED_PREFERENCES_STORE = "HueSharedPrefs"
        private val LAST_CONNECTED_USERNAME = "LastConnectedUsername"
        private val LAST_CONNECTED_IP = "LastConnectedIP"
        private var instance: HueSharedPreferences? = null

        fun getInstance(ctx: Context): HueSharedPreferences {
            if (instance == null) {
                instance = HueSharedPreferences(ctx)
            }
            return instance as HueSharedPreferences
        }
    }
}
