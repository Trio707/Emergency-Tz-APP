package tz.emergency.app.dialer

interface DialerFactory {
    fun create(context: Any, activity: Any?): Dialer
}