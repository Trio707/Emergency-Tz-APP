package tz.emergency.app.config

actual class BuildConfig {
    actual companion object {
        // On iOS, DEBUG is determined by the build configuration
        // This can be set via a compile-time constant or Info.plist
        actual val DEBUG: Boolean = false // Will be overridden by build config
        
        // DEBUG_TEST_PHONE_NUMBER for iOS (not used for call safety, but for consistency)
        actual val DEBUG_TEST_PHONE_NUMBER: String? = null
    }
}