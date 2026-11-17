package tz.emergency.app.dialer

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import tz.emergency.app.EmergencyService
import tz.emergency.app.config.BuildConfig

class AndroidDialer : Dialer {

    private var context: Context? = null
    private var activity: Activity? = null

    companion object {
        fun create(context: Context, activity: Activity? = null): AndroidDialer {
            val dialer = AndroidDialer()
            dialer.context = context
            dialer.activity = activity
            return dialer
        }
    }

    private val ctx: Context get() = context!!
    private val act: Activity? get() = activity

    override fun requestPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        act?.let { act ->
            if (act is androidx.activity.ComponentActivity) {
                val launcher = act.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                    if (!granted) {
                        Toast.makeText(
                            ctx,
                            "Call permission is required to contact emergency services from this app.",
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                }
                launcher.launch(Manifest.permission.CALL_PHONE)
            }
        }
        return false
    }

    override fun placeCall(service: EmergencyService): Boolean {
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }

        val numberToCall = numberToCall(service) ?: return false

        try {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$numberToCall"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ctx.startActivity(intent)
            return true
        } catch (e: SecurityException) {
            Toast.makeText(ctx, "Unable to place the call.", Toast.LENGTH_LONG).show()
            return false
        } catch (e: android.content.ActivityNotFoundException) {
            Toast.makeText(ctx, "No calling app is available on this device.", Toast.LENGTH_LONG).show()
            return false
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

        Toast.makeText(
            ctx,
            "Test mode: add your number to DEBUG_TEST_PHONE_NUMBER in local.properties before calling.",
            Toast.LENGTH_LONG,
        ).show()
        return null
    }
}