package tz.emergency.app.prefs

interface Preferences {
    fun getPermissionRequested(): Boolean
    fun setPermissionRequested(value: Boolean)
}

interface PreferencesFactory {
    fun create(context: Any): Preferences
}