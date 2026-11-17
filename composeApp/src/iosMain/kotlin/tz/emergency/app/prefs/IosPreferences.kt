package tz.emergency.app.prefs

import platform.Foundation.NSUserDefaults

actual class IosPreferences : Preferences() {

    private val userDefaults = NSUserDefaults.standardUserDefaults

    companion object {
        private const val KEY_PERMISSION_REQUESTED = "call_permission_requested"
    }

    override fun getPermissionRequested(): Boolean =
        userDefaults.boolForKey(KEY_PERMISSION_REQUESTED)

    override fun setPermissionRequested(value: Boolean) {
        userDefaults.setBool(value, KEY_PERMISSION_REQUESTED)
        userDefaults.synchronize()
    }
}

actual class PreferencesFactory {
    actual companion object {
        actual fun create(): Preferences = IosPreferences()
    }
}