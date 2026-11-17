package tz.emergency.app.dialer

import tz.emergency.app.EmergencyService

interface Dialer {
    /**
     * Requests permission to make phone calls.
     * Returns true if permission is granted or not needed (iOS).
     * Returns false if permission was denied.
     */
    fun requestPermission(): Boolean

    /**
     * Places an emergency call for the given service.
     * Returns true if call was initiated, false otherwise.
     */
    fun placeCall(service: EmergencyService): Boolean

    /**
     * Returns true if running in debug mode.
     */
    fun isDebugMode(): Boolean

    /**
     * Returns the test phone number for debug builds, or null if not set.
     */
    fun getTestPhoneNumber(): String?
}