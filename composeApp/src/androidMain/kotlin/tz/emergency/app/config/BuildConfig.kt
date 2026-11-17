package tz.emergency.app.config

actual class BuildConfig {
    actual companion object {
        actual val DEBUG: Boolean = tz.emergency.app.BuildConfig.DEBUG
        actual val DEBUG_TEST_PHONE_NUMBER: String? = tz.emergency.app.BuildConfig.DEBUG_TEST_PHONE_NUMBER
    }
}