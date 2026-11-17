package tz.emergency.app.config

expect class BuildConfig {
    companion object {
        val DEBUG: Boolean
        val DEBUG_TEST_PHONE_NUMBER: String?
    }
}