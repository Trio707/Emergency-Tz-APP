package tz.emergency.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color

private val EmergencyColorScheme = lightColorScheme(
    background = Color(0xFFF7F8FA),
    surface = Color(0xFFF7F8FA),
    onBackground = Color(0xFF171D25),
)

private val EmergencyDarkColorScheme = darkColorScheme(
    background = Color(0xFF111820),
    surface = Color(0xFF111820),
    onBackground = Color(0xFFE7EDF5),
)

@Composable
fun EmergencyTanzaniaTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) EmergencyDarkColorScheme else EmergencyColorScheme
    MaterialTheme(colorScheme = colors, content = content)
}
