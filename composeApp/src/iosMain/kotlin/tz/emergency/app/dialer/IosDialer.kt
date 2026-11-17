package tz.emergency.app.dialer

import kotlinx.cinterop.*
import objc.NSObject
import objc.UIKit.UIApplication
import objc.UIKit.NSURL
import platform.iosFoundation.NSURLComponents
import tz.emergency.app.EmergencyService
import tz.emergency.app.config.BuildConfig

actual class IosDialer : Dialer() {

    override fun requestPermission(): Boolean {
        // iOS doesn't require special permission for tel:// URLs
        // The user is always prompted before a call is placed
        return true
    }

    override fun placeCall(service: EmergencyService): Boolean {
        val numberToCall = numberToCall(service) ?: return false

        val urlString = "tel://$numberToCall"
        val url = NSURLComponents.alloc().init().apply {
            scheme = "tel"
            string = urlString
        }.url

        return if (url != null) {
            val application = UIApplication.sharedApplication
            if (application.canOpenURL(url)) {
                application.openURL(url)
                true
            } else {
                false
            }
        } else {
            false
        }
    }

    override fun isDebugMode(): Boolean = BuildConfig.DEBUG

    override fun getTestPhoneNumber(): String? {
        val testNumber = BuildConfig.DEBUG_TEST_PHONE_NUMBER?.trim()
        return if (testNumber?.isNotEmpty() == true) testNumber else null
    }

    private fun numberToCall(service: EmergencyService): String? {
        if (!BuildConfig.DEBUG) return service.phoneNumber

        val testNumber = getTestPhoneNumber()
        if (testNumber != null) return testNumber

        // In debug mode without test number, we still allow the call
        // since iOS always prompts the user anyway
        return service.phoneNumber
    }
}