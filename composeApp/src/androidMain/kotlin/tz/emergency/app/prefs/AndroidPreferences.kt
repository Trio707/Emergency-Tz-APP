package tz.emergency.app.prefs

import android.content.Context
import android.content.SharedPreferences

class AndroidPreferences : Preferences {

    private var sharedPreferences: SharedPreferences? = null

    companion object {
        fun create(context: Context): AndroidPreferences {
            val prefs = AndroidPreferences()
            prefs.sharedPreferences = context.getSharedPreferences("emergency_tanzania", Context.MODE_PRIVATE)
            return prefs
        }
    }

    private val prefs: SharedPreferences get() = sharedPreferences!!

    override fun getPermissionRequested(): Boolean =
        prefs.getBoolean("call_permission_requested", false)

    override fun setPermissionRequested(value: Boolean) {
        prefs.edit().putBoolean("call_permission_requested", value).apply()
    }
}

class AndroidPreferencesFactory : PreferencesFactory {
    override fun create(context: Any): Preferences {
        return when (context) {
            is Context -> AndroidPreferences.create(context)
            else -> throw IllegalArgumentException("Invalid context type")
        }
    }
}