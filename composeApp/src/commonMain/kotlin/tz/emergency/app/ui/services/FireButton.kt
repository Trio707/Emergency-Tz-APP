package tz.emergency.app.ui.services

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FireButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    EmergencyServiceButton(
        label = "FIRE",
        icon = FireIcon,
        containerColor = Color(0xFFC62828),
        contentColor = Color.White,
        modifier = modifier,
        onClick = onClick,
    )
}