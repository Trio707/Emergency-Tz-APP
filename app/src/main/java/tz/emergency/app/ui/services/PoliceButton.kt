package tz.emergency.app.ui.services

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PoliceButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    EmergencyServiceButton(
        label = "POLICE", icon = PoliceIcon, containerColor = Color(0xFF0756A4),
        contentColor = Color.White, modifier = modifier, onClick = onClick,
    )
}

