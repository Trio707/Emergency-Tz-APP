package tz.emergency.app.ui.services

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AmbulanceButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    EmergencyServiceButton(
        label = "AMBULANCE",
        icon = AmbulanceIcon,
        containerColor = Color(0xFF2E7D32),
        contentColor = Color.White,
        modifier = modifier,
        onClick = onClick,
    )
}