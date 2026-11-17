package tz.emergency.app.dialer

import tz.emergency.app.EmergencyService

actual class DialerFactory {
    actual companion object {
        actual fun create(): Dialer = IosDialer()
    }
}