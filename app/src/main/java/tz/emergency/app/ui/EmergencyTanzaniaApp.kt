package tz.emergency.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tz.emergency.app.EmergencyService
import tz.emergency.app.ui.services.AmbulanceButton
import tz.emergency.app.ui.services.FireButton
import tz.emergency.app.ui.services.PoliceButton

@Composable
fun EmergencyTanzaniaApp(
    onServiceSelected: (EmergencyService) -> Unit,
    testPhoneNumber: String?,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = "EMERGENCY",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "TANZANIA EMERGENCY SERVICES",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.72f),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Tap a service to call immediately.",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.82f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 12.dp),
            )
            if (testPhoneNumber != null) {
                Text(
                    text = "TEST MODE • Calls $testPhoneNumber",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.82f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            }
            PoliceButton(onClick = { onServiceSelected(EmergencyService.POLICE) })
            FireButton(
                onClick = { onServiceSelected(EmergencyService.FIRE_AND_RESCUE) },
            )
            AmbulanceButton(
                onClick = { onServiceSelected(EmergencyService.AMBULANCE) },
            )
            Text(
                text = "Use only in an emergency. Calls require an available cellular network.",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.72f),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}
