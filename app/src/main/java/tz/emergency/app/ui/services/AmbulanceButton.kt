package tz.emergency.app.ui.services

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AmbulanceButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    EmergencyServiceButton(
        label = "AMBULANCE", icon = AmbulanceIcon, containerColor = Color.White,
        contentColor = Color(0xFF171D25), modifier = modifier,
        border = BorderStroke(2.dp, Color(0xFF303842)), onClick = onClick,
    )
}

