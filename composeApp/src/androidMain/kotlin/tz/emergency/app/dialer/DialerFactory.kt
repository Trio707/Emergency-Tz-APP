package tz.emergency.app.dialer

import android.app.Activity
import android.content.Context

class AndroidDialerFactory : DialerFactory {
    override fun create(context: Any, activity: Any?): Dialer {
        return when (context) {
            is Context -> {
                val act = activity as? Activity
                AndroidDialer.create(context, act)
            }
            else -> throw IllegalArgumentException("Invalid context type")
        }
    }
}