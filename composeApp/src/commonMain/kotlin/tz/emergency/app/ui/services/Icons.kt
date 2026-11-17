package tz.emergency.app.ui.services

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val PoliceIcon = ImageVector.Builder("Police", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color.Black)) {
        moveTo(12f, 2f); lineTo(3f, 6f); verticalLineTo(11f); curveTo(3f, 16.55f, 6.84f, 21.74f, 12f, 23f)
        curveTo(17.16f, 21.74f, 21f, 16.55f, 21f, 11f); verticalLineTo(6f); close()
        moveTo(12f, 7f); lineTo(13.2f, 10.1f); lineTo(16.5f, 10.25f); lineTo(13.9f, 12.3f); lineTo(14.8f, 15.5f)
        lineTo(12f, 13.6f); lineTo(9.2f, 15.5f); lineTo(10.1f, 12.3f); lineTo(7.5f, 10.25f); lineTo(10.8f, 10.1f); close()
    }
}.build()

internal val FireIcon = ImageVector.Builder("Fire", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color.Black)) {
        moveTo(12f, 2f); curveTo(12f, 2f, 5f, 9.1f, 5f, 14.3f); curveTo(5f, 18.55f, 8.13f, 22f, 12f, 22f)
        curveTo(15.87f, 22f, 19f, 18.55f, 19f, 14.3f); curveTo(19f, 10.1f, 15.7f, 6.25f, 13.5f, 4f)
        curveTo(13.9f, 7.35f, 12.5f, 9f, 10.6f, 10.55f); curveTo(10.15f, 7.8f, 11.1f, 5f, 12f, 2f)
        moveTo(12f, 20f); curveTo(10.1f, 20f, 8.6f, 18.35f, 8.6f, 16.3f); curveTo(8.6f, 14.35f, 10.1f, 12.4f, 11.6f, 11f)
        curveTo(11.4f, 13.25f, 13f, 14f, 14.1f, 15.35f); curveTo(14.8f, 16.2f, 15.1f, 17.1f, 15.1f, 17.8f)
        curveTo(15.1f, 19f, 13.75f, 20f, 12f, 20f); close()
    }
}.build()

internal val AmbulanceIcon = ImageVector.Builder("Ambulance", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color.Black)) {
        moveTo(4f, 4f); horizontalLineTo(14f); verticalLineTo(7f); horizontalLineTo(17.5f); lineTo(21f, 11.5f); verticalLineTo(17f)
        horizontalLineTo(19f); curveTo(19f, 18.65f, 17.65f, 20f, 16f, 20f); curveTo(14.35f, 20f, 13f, 18.65f, 13f, 17f)
        horizontalLineTo(9f); curveTo(9f, 18.65f, 7.65f, 20f, 6f, 20f); curveTo(4.35f, 20f, 3f, 18.65f, 3f, 17f); verticalLineTo(6f); close()
        moveTo(16f, 9f); verticalLineTo(12f); horizontalLineTo(19f); lineTo(16.7f, 9f); close()
        moveTo(8f, 7f); horizontalLineTo(10f); verticalLineTo(9f); horizontalLineTo(12f); verticalLineTo(11f); horizontalLineTo(10f); verticalLineTo(13f); horizontalLineTo(8f); verticalLineTo(11f); horizontalLineTo(6f); verticalLineTo(9f); horizontalLineTo(8f); close()
    }
}.build()