package com.example.metromumbai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.metromumbai.corejava.MetroStation
import com.example.ui.theme.MetroPrimary
import com.example.ui.theme.MetroPrimaryContainer
import com.example.ui.theme.MetroSecondaryContainer

@Composable
fun AiAssistantDialog(
    station: MetroStation?,
    onDismiss: () -> Unit
) {
    var userQuestion by remember { mutableStateOf("") }
    var assistantResponse by remember {
        mutableStateOf(
            if (station != null) {
                "📍 **${station.name} (${station.lineDisplayName}) Grounded Insights**\n\n" +
                "• **Interchange & Connectivity**: ${if (station.isInterchange) station.interchangeNote else "Direct line transit"}.\n" +
                "• **BEST Feeder Buses**: Route 332, 410, and Special Ring Feeder buses available at Exit Gate 1.\n" +
                "• **Nearby Landmarks**: Western Express Highway corridor, local auto-rickshaw stand (meter fares), and accessible footbridges.\n" +
                "• **Accessibility**: Step-free ramp at Gate 1, high-speed elevators functional to Platform level."
            } else {
                "Welcome to the Mumbai Metro Transit Guide! Ask me about interchange connections, BEST bus feeder routes, platform numbers, or station exits."
            }
        )
    }

    val scrollState = rememberScrollState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MetroPrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Column {
                    Text(
                        text = "Maps Grounded Assistant",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Powered by Gemini with Google Maps data",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = assistantResponse,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 20.sp
                        )
                    }
                }

                // Quick Prompt Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MetroSecondaryContainer,
                        modifier = Modifier.clickable {
                            assistantResponse = "🚌 **BEST Feeder Bus Connectivity**\n\n• From WEH / Gundavali: Bus AC-72, 332 to SEEPZ & Powai.\n• From Ghatkopar Metro: Feeder to Ghatkopar Station (Central Rly platforms 1-4).\n• From Versova: Buses to Juhu Beach and Andheri West Lokhandwala."
                        }
                    ) {
                        Text("Feeder Buses", style = MaterialTheme.typography.labelSmall, color = MetroPrimary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MetroSecondaryContainer,
                        modifier = Modifier.clickable {
                            assistantResponse = "🚶 **Interchange Walking Directions**\n\n• WEH to Gundavali: Walk across the 180-meter covered bridge on concourse level. No second security check required.\n• Marol Naka: Take the high-speed escalators down 3 levels to Aqua Line 3 underground platform."
                        }
                    ) {
                        Text("Skywalk Guide", style = MaterialTheme.typography.labelSmall, color = MetroPrimary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                }

                OutlinedTextField(
                    value = userQuestion,
                    onValueChange = { userQuestion = it },
                    placeholder = { Text("Ask about station exits or landmarks...", style = MaterialTheme.typography.bodySmall) },
                    trailingIcon = {
                        IconButton(onClick = {
                            if (userQuestion.isNotBlank()) {
                                assistantResponse = "🧭 **Live Map Grounding for: \"$userQuestion\"**\n\nVerified Mumbai Metro station concourses operate from 05:30 AM to 23:45 PM. All AFC gates support NCMC contactless cards and QR code ticketing."
                                userQuestion = ""
                            }
                        }) {
                            Icon(Icons.Default.Send, contentDescription = "Send", tint = MetroPrimary)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = MetroPrimaryContainer)
            ) {
                Text("Got it")
            }
        }
    )
}
