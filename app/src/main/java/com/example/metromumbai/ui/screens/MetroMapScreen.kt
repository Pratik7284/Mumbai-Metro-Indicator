package com.example.metromumbai.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Atm
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Escalator
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schema
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TripOrigin
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WheelchairPickup
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.metromumbai.corejava.MetroStation
import com.example.metromumbai.data.MetroUiState
import com.example.metromumbai.data.MetroViewModel
import com.example.ui.theme.Line1Blue
import com.example.ui.theme.Line2AYellow
import com.example.ui.theme.Line3Aqua
import com.example.ui.theme.Line7Red
import com.example.ui.theme.MetroPrimary
import com.example.ui.theme.MetroPrimaryContainer
import com.example.ui.theme.MetroSecondaryContainer

@Composable
fun MetroMapScreen(
    uiState: MetroUiState,
    viewModel: MetroViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var zoomLevel by remember { mutableFloatStateOf(1f) }
    var isSchematic by remember { mutableStateOf(true) }
    var isSetAsStartSuccess by remember { mutableStateOf(false) }

    val filterScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseSize by infiniteTransition.animateFloat(
        initialValue = 16f,
        targetValue = 28f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseSize"
    )

    val selectedStation = uiState.selectedMapStation ?: viewModel.getStationById("L1_MAROL_NAKA")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(verticalScrollState)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Top Search Bar
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            shadowElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MetroPrimary,
                    modifier = Modifier.size(20.dp)
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { query ->
                        searchQuery = query
                        if (query.isNotEmpty()) {
                            val matched = viewModel.getFilteredStations().firstOrNull {
                                it.name.contains(query, ignoreCase = true)
                            }
                            if (matched != null) {
                                viewModel.selectMapStation(matched)
                            }
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Find station on map...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("map_search_input")
                )

                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                IconButton(onClick = {
                    viewModel.showToast("Voice search activated. Say station name...")
                }) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Voice Search",
                        tint = MetroPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Line Filter Pills Ribbon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(filterScrollState)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MapLineChip(
                label = "All Lines",
                dotColor = Color.White,
                isSelected = uiState.activeMapLineFilter == "all",
                onClick = { viewModel.setMapLineFilter("all") }
            )
            MapLineChip(
                label = "Line 1 Blue",
                dotColor = Line1Blue,
                isSelected = uiState.activeMapLineFilter == "line1",
                onClick = { viewModel.setMapLineFilter("line1") }
            )
            MapLineChip(
                label = "Line 2A Yellow",
                dotColor = Line2AYellow,
                isSelected = uiState.activeMapLineFilter == "line2a",
                onClick = { viewModel.setMapLineFilter("line2a") }
            )
            MapLineChip(
                label = "Line 3 Aqua",
                dotColor = Line3Aqua,
                isSelected = uiState.activeMapLineFilter == "line3",
                onClick = { viewModel.setMapLineFilter("line3") }
            )
            MapLineChip(
                label = "Line 7 Red",
                dotColor = Line7Red,
                isSelected = uiState.activeMapLineFilter == "line7",
                onClick = { viewModel.setMapLineFilter("line7") }
            )
        }

        // Metro Canvas Viewport Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(460.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
        ) {
            // Schematic / Geographic Canvas
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            val scale = zoomLevel
                            val w = size.width
                            val h = size.height

                            // Map relative tap coordinates back to stations
                            val l1y = h * 0.48f
                            val l2ax = w * 0.32f
                            val l7x = w * 0.65f
                            val l3marolX = w * 0.72f

                            if (kotlin.math.abs(offset.y - l1y) < 40f && kotlin.math.abs(offset.x - l3marolX) < 40f) {
                                viewModel.getStationById("L1_MAROL_NAKA")?.let { viewModel.selectMapStation(it) }
                            } else if (kotlin.math.abs(offset.y - l1y) < 40f && kotlin.math.abs(offset.x - l7x) < 40f) {
                                viewModel.getStationById("L1_WEH")?.let { viewModel.selectMapStation(it) }
                            } else if (kotlin.math.abs(offset.y - l1y) < 40f && kotlin.math.abs(offset.x - l2ax) < 40f) {
                                viewModel.getStationById("L1_DN_NAGAR")?.let { viewModel.selectMapStation(it) }
                            } else if (offset.y < h * 0.2f) {
                                viewModel.getStationById("L7_DAHISAR_E")?.let { viewModel.selectMapStation(it) }
                            } else if (offset.y > h * 0.75f) {
                                viewModel.getStationById("L3_BKC")?.let { viewModel.selectMapStation(it) }
                            } else if (offset.x < w * 0.25f) {
                                viewModel.getStationById("L1_VERSOVA")?.let { viewModel.selectMapStation(it) }
                            } else if (offset.x > w * 0.8f) {
                                viewModel.getStationById("L1_GHATKOPAR")?.let { viewModel.selectMapStation(it) }
                            }
                        }
                    }
            ) {
                val w = size.width
                val h = size.height
                val filter = uiState.activeMapLineFilter

                // Subtle Coastline & Geography Accents (Peninsula stylized wave)
                val seaPath = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(w * 0.18f, 0f)
                    quadraticTo(w * 0.25f, h * 0.35f, w * 0.15f, h * 0.7f)
                    quadraticTo(w * 0.10f, h * 0.85f, w * 0.05f, h)
                    lineTo(0f, h)
                    close()
                }
                drawPath(seaPath, color = Color(0xFFD6E4FF).copy(alpha = 0.5f))

                // Line 2A Track (Yellow: Dahisar East down to DN Nagar)
                val l2aAlpha = if (filter == "all" || filter == "line2a") 1f else 0.2f
                val l2ax = w * 0.32f
                val l2ay1 = h * 0.10f
                val l2ay2 = h * 0.48f

                drawLine(
                    color = Line2AYellow.copy(alpha = l2aAlpha),
                    start = Offset(l2ax, l2ay1),
                    end = Offset(l2ax, l2ay2),
                    strokeWidth = 14f,
                    cap = StrokeCap.Round
                )

                // Line 7 Track (Red: Dahisar East to Gundavali)
                val l7Alpha = if (filter == "all" || filter == "line7") 1f else 0.2f
                val l7x = w * 0.65f
                val l7y1 = h * 0.10f
                val l7y2 = h * 0.48f

                // North loop connecting Dahisar
                val l7Path = Path().apply {
                    moveTo(l2ax, l2ay1)
                    lineTo(l7x, l7y1)
                    lineTo(l7x, l7y2)
                }
                drawPath(
                    l7Path,
                    color = Line7Red.copy(alpha = l7Alpha),
                    style = Stroke(width = 14f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                )

                // Line 1 Track (Blue: Versova -> Ghatkopar)
                val l1Alpha = if (filter == "all" || filter == "line1") 1f else 0.2f
                val l1y = h * 0.48f
                drawLine(
                    color = Line1Blue.copy(alpha = l1Alpha),
                    start = Offset(w * 0.14f, l1y),
                    end = Offset(w * 0.88f, l1y),
                    strokeWidth = 14f,
                    cap = StrokeCap.Round
                )

                // Line 3 Track (Aqua: Aarey -> Marol -> Airport T2 -> Airport T1 -> BKC)
                val l3Alpha = if (filter == "all" || filter == "line3") 1f else 0.2f
                val l3Path = Path().apply {
                    moveTo(w * 0.72f, h * 0.26f) // Aarey JVLR
                    lineTo(w * 0.72f, l1y)       // Marol Naka
                    lineTo(w * 0.58f, h * 0.65f) // Airport T2
                    lineTo(w * 0.46f, h * 0.78f) // Airport T1
                    lineTo(w * 0.46f, h * 0.92f) // BKC
                }
                drawPath(
                    l3Path,
                    color = Line3Aqua.copy(alpha = l3Alpha),
                    style = Stroke(width = 14f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                )

                // Intermediate Station Dots
                // Line 2A stops
                drawCircle(Color.White, radius = 7f, center = Offset(l2ax, h * 0.22f))
                drawCircle(Line2AYellow, radius = 4f, center = Offset(l2ax, h * 0.22f))
                drawCircle(Color.White, radius = 7f, center = Offset(l2ax, h * 0.35f))
                drawCircle(Line2AYellow, radius = 4f, center = Offset(l2ax, h * 0.35f))

                // Line 7 stops
                drawCircle(Color.White, radius = 7f, center = Offset(l7x, h * 0.22f))
                drawCircle(Line7Red, radius = 4f, center = Offset(l7x, h * 0.22f))
                drawCircle(Color.White, radius = 7f, center = Offset(l7x, h * 0.35f))
                drawCircle(Line7Red, radius = 4f, center = Offset(l7x, h * 0.35f))

                // Line 1 stops: Versova, Andheri, Chakala, Ghatkopar
                drawCircle(Color.White, radius = 9f, center = Offset(w * 0.14f, l1y))
                drawCircle(Line1Blue, radius = 5f, center = Offset(w * 0.14f, l1y))

                drawCircle(Color.White, radius = 7f, center = Offset(w * 0.48f, l1y))
                drawCircle(Line1Blue, radius = 4f, center = Offset(w * 0.48f, l1y))

                drawCircle(Color.White, radius = 9f, center = Offset(w * 0.88f, l1y))
                drawCircle(Line1Blue, radius = 5f, center = Offset(w * 0.88f, l1y))

                // Line 3 stops: Airport T2, Airport T1, BKC
                drawCircle(Color.White, radius = 8f, center = Offset(w * 0.58f, h * 0.65f))
                drawCircle(Line3Aqua, radius = 5f, center = Offset(w * 0.58f, h * 0.65f))

                drawCircle(Color.White, radius = 8f, center = Offset(w * 0.46f, h * 0.78f))
                drawCircle(Line3Aqua, radius = 5f, center = Offset(w * 0.46f, h * 0.78f))

                drawCircle(Color.White, radius = 10f, center = Offset(w * 0.46f, h * 0.92f))
                drawCircle(Line3Aqua, radius = 6f, center = Offset(w * 0.46f, h * 0.92f))

                // MAJOR INTERCHANGES
                // 1. Dahisar East
                drawCircle(Color(0xFF0B1C30), radius = 14f, center = Offset(l2ax, l2ay1))
                drawCircle(Color.White, radius = 9f, center = Offset(l2ax, l2ay1))
                drawCircle(Line2AYellow, radius = 5f, center = Offset(l2ax, l2ay1))

                // 2. DN Nagar
                drawCircle(Color(0xFF0B1C30), radius = 14f, center = Offset(l2ax, l1y))
                drawCircle(Color.White, radius = 9f, center = Offset(l2ax, l1y))
                drawCircle(Line1Blue, radius = 5f, center = Offset(l2ax, l1y))

                // 3. Gundavali / WEH
                drawCircle(Color(0xFF0B1C30), radius = 14f, center = Offset(l7x, l1y))
                drawCircle(Color.White, radius = 9f, center = Offset(l7x, l1y))
                drawCircle(Line7Red, radius = 5f, center = Offset(l7x, l1y))

                // 4. Marol Naka (Active highlight with pulsing ring)
                val marolX = w * 0.72f
                drawCircle(
                    MetroPrimary.copy(alpha = 0.35f),
                    radius = pulseSize,
                    center = Offset(marolX, l1y),
                    style = Stroke(width = 3f)
                )
                drawCircle(MetroPrimary, radius = 16f, center = Offset(marolX, l1y))
                drawCircle(Color.White, radius = 10f, center = Offset(marolX, l1y))
                drawCircle(Line3Aqua, radius = 6f, center = Offset(marolX, l1y))

                // Labels with Android nativeCanvas
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.parseColor("#0B1C30")
                    textSize = 28f
                    isFakeBoldText = true
                    textAlign = android.graphics.Paint.Align.CENTER
                }

                drawContext.canvas.nativeCanvas.drawText("Marol Naka", marolX, l1y - 24f, paint)
                drawContext.canvas.nativeCanvas.drawText("Gundavali / WEH", l7x, l1y + 36f, paint)
                drawContext.canvas.nativeCanvas.drawText("DN Nagar", l2ax, l1y + 36f, paint)
                drawContext.canvas.nativeCanvas.drawText("BKC Terminal", w * 0.46f, h * 0.92f + 36f, paint)
            }

            // Live Status Pill (Top Left)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.95f),
                shadowElevation = 2.dp,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF10B981))
                    )
                    Text(
                        text = "All 4 Lines Operational",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Floating Map Controls (Right Side)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.95f),
                shadowElevation = 3.dp,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopEnd)
            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    IconButton(
                        onClick = { if (zoomLevel < 1.6f) zoomLevel += 0.2f },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Zoom In", tint = MaterialTheme.colorScheme.onSurface)
                    }
                    IconButton(
                        onClick = { if (zoomLevel > 0.8f) zoomLevel -= 0.2f },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Zoom Out", tint = MaterialTheme.colorScheme.onSurface)
                    }
                    IconButton(
                        onClick = {
                            zoomLevel = 1f
                            viewModel.getStationById("L1_MAROL_NAKA")?.let { viewModel.selectMapStation(it) }
                        },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(Icons.Default.MyLocation, contentDescription = "Recenter", tint = MetroPrimary)
                    }
                    IconButton(
                        onClick = {
                            isSchematic = !isSchematic
                            viewModel.showToast(if (isSchematic) "Switched to Schematic View" else "Switched to Geographic View")
                        },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(Icons.Default.Schema, contentDescription = "Toggle Schema", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            // Mini Visual Map Key Pill (Bottom Left)
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.95f),
                shadowElevation = 1.dp,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.BottomStart)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MapKeyDot("L1", Line1Blue)
                    MapKeyDot("L2A", Line2AYellow)
                    MapKeyDot("L3", Line3Aqua)
                    MapKeyDot("L7", Line7Red)
                }
            }
        }

        // Bottom Sheet Overlay: Selected Station Preview Card
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            shadowElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Drag handle indicator
                Box(
                    modifier = Modifier
                        .size(width = 36.dp, height = 4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                        .align(Alignment.CenterHorizontally)
                )

                // Station Title & Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = selectedStation?.name ?: "Marol Naka Interchange",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = MetroPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "Connects Line 1 (Blue) & Line 3 (Aqua Line)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MetroSecondaryContainer
                    ) {
                        Text(
                            text = "Interchange",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Live Next Trains Countdown Card
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLow
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "LIVE NEXT TRAINS",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                letterSpacing = 0.8.sp
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF10B981))
                                )
                                Text(
                                    text = "Live GPS",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF10B981),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Departure 1
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                                shadowElevation = 1.dp,
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(Line1Blue)
                                        )
                                        Text(
                                            text = "To Ghatkopar",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Bottom
                                    ) {
                                        Text(
                                            text = "2 min",
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = MetroPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Plat 2",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }

                            // Departure 2
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                                shadowElevation = 1.dp,
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(Line3Aqua)
                                        )
                                        Text(
                                            text = "To BKC (Aqua)",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Bottom
                                    ) {
                                        Text(
                                            text = "4 min",
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = Color(0xFF006194),
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Plat 1",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Station Amenities Quick Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.WheelchairPickup,
                            contentDescription = null,
                            tint = MetroPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text("Step-free", style = MaterialTheme.typography.bodySmall)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Escalator,
                            contentDescription = null,
                            tint = MetroPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text("Escalators", style = MaterialTheme.typography.bodySmall)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Atm,
                            contentDescription = null,
                            tint = MetroPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text("ATMs & Gates", style = MaterialTheme.typography.bodySmall)
                    }
                }

                // Action Buttons Pair
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            if (selectedStation != null) {
                                viewModel.selectStationForRole(selectedStation)
                                isSetAsStartSuccess = true
                                viewModel.showToast("Set ${selectedStation.name} as journey start!")
                            }
                        },
                        modifier = Modifier
                            .testTag("set_as_start_button")
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSetAsStartSuccess) Color(0xFF10B981) else MetroPrimaryContainer,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = if (isSetAsStartSuccess) Icons.Default.Check else Icons.Default.TripOrigin,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isSetAsStartSuccess) "Selected as Start" else "Set as Start",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Button(
                        onClick = {
                            if (selectedStation != null) {
                                viewModel.openStationDetails(selectedStation)
                            }
                        },
                        modifier = Modifier
                            .testTag("station_info_button")
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            contentColor = MetroPrimary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Station Info",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MapLineChip(
    label: String,
    dotColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) MetroPrimaryContainer else MaterialTheme.colorScheme.surfaceContainerLowest,
        shadowElevation = 1.dp,
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

@Composable
private fun MapKeyDot(label: String, dotColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(dotColor)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
