package tz.emergency.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import tz.emergency.app.ui.EmergencyTanzaniaApp
import tz.emergency.app.ui.theme.EmergencyTanzaniaTheme

class MainActivity : ComponentActivity() {
    private val callPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (!granted) {
            Toast.makeText(
                this,
                "Call permission is required to contact emergency services from this app.",
                Toast.LENGTH_LONG,
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestCallPermissionOnFirstLaunch()
        setContent {
            EmergencyTanzaniaTheme {
                EmergencyTanzaniaApp(
                    onServiceSelected = ::placeEmergencyCall,
                    testPhoneNumber = BuildConfig.DEBUG_TEST_PHONE_NUMBER
                        .trim()
                        .takeIf { BuildConfig.DEBUG && it.isNotEmpty() },
                )
            }
        }
    }

    private fun requestCallPermissionOnFirstLaunch() {
        val preferences = getSharedPreferences("emergency_tanzania", MODE_PRIVATE)
        val alreadyRequested = preferences.getBoolean("call_permission_requested", false)
        if (!alreadyRequested && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            preferences.edit().putBoolean("call_permission_requested", true).apply()
            callPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
        }
    }

    private fun placeEmergencyCall(service: EmergencyService) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            showCallPermissionSettings()
            return
        }

        try {
            val numberToCall = numberToCall(service) ?: return
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$numberToCall")))
        } catch (exception: SecurityException) {
            Toast.makeText(this, "Unable to place the call.", Toast.LENGTH_LONG).show()
        } catch (exception: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "No calling app is available on this device.", Toast.LENGTH_LONG).show()
        }
    }

    private fun numberToCall(service: EmergencyService): String? {
        if (!BuildConfig.DEBUG) return service.phoneNumber

        val testNumber = BuildConfig.DEBUG_TEST_PHONE_NUMBER.trim()
        if (testNumber.isNotEmpty()) return testNumber

        Toast.makeText(
            this,
            "Test mode: add your number to DEBUG_TEST_PHONE_NUMBER in local.properties before calling.",
            Toast.LENGTH_LONG,
        ).show()
        return null
    }

    private fun showCallPermissionSettings() {
        android.app.AlertDialog.Builder(this)
            .setTitle("Call permission required")
            .setMessage("Enable phone-call permission in Settings to contact emergency services from this app.")
            .setPositiveButton("Open Settings") { _, _ ->
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", packageName, null),
                    ),
                )
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
