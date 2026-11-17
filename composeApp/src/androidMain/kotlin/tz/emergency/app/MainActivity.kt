package tz.emergency.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import tz.emergency.app.dialer.AndroidDialerFactory
import tz.emergency.app.prefs.AndroidPreferencesFactory
import tz.emergency.app.ui.EmergencyTanzaniaApp
import tz.emergency.app.ui.theme.EmergencyTanzaniaTheme

class MainActivity : ComponentActivity() {

    private val dialer = AndroidDialerFactory().create(this, this)
    private val prefs = AndroidPreferencesFactory().create(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestCallPermissionOnFirstLaunch()

        setContent {
            EmergencyTanzaniaTheme {
                EmergencyTanzaniaApp(
                    onServiceSelected = { service ->
                        dialer.placeCall(service)
                    },
                    testPhoneNumber = if (dialer.isDebugMode()) dialer.getTestPhoneNumber() else null,
                )
            }
        }
    }

    private fun requestCallPermissionOnFirstLaunch() {
        val alreadyRequested = prefs.getPermissionRequested()
        if (!alreadyRequested) {
            prefs.setPermissionRequested(true)
            dialer.requestPermission()
        }
    }
}