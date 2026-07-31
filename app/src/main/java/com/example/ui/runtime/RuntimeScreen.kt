package com.example.ui.runtime

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.InstalledPackage
import com.example.ui.theme.Win11Background
import com.example.ui.theme.Win11Blue
import com.example.ui.theme.Win11Border
import com.example.ui.theme.Win11Success
import com.example.ui.theme.Win11Surface
import com.example.ui.theme.Win11SurfaceCard
import com.example.ui.theme.Win11SurfaceElevated
import com.example.ui.theme.Win11TextPrimary
import com.example.ui.theme.Win11TextSecondary
import com.example.viewmodel.WindowsAndroidOsViewModel

@Composable
fun RuntimeScreen(
    pkg: InstalledPackage,
    viewModel: WindowsAndroidOsViewModel,
    onClose: () -> Unit
) {
    val config by viewModel.engineConfig.collectAsState()
    val fps by viewModel.currentFps.collectAsState()
    val logs by viewModel.runtimeLog.collectAsState()
    var showLogs by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // 16:9 Aspect Ratio Immersive Fullscreen Rendering Canvas
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF141A24),
                                Color(0xFF0F131A)
                            )
                        )
                    )
            ) {
                // Simulated 3D / Roblox Canvas Render
                RobloxSimulatedCanvas(
                    packageName = pkg.name,
                    graphicsApi = config.exeGraphicsApi,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Top HUD Status Bar (Windows 11 Fluent Acrylic Bar)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                color = Win11Surface.copy(alpha = 0.9f),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Win11Border)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // App Name & Active Engine
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SportsEsports,
                            contentDescription = null,
                            tint = Win11Blue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = pkg.name,
                                color = Win11TextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Engine: ${pkg.type.label} • API 28+ Hybrid Runtime",
                                color = Win11TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // FPS & Graphics Status Pill
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatusChip(
                            icon = Icons.Default.Speed,
                            label = "$fps FPS (${config.fpsLimit})",
                            color = Win11Success
                        )

                        StatusChip(
                            icon = Icons.Default.Tv,
                            label = "Grafik: ${config.androidGraphicsApi} / ${config.exeGraphicsApi}",
                            color = Win11Blue
                        )

                        StatusChip(
                            icon = Icons.Default.Memory,
                            label = "VRAM: +${config.virtualRamSize}",
                            color = Color(0xFF8A2BE2)
                        )

                        // Toggle log inspector
                        Button(
                            onClick = { showLogs = !showLogs },
                            colors = ButtonDefaults.buttonColors(containerColor = Win11SurfaceElevated),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(
                                text = if (showLogs) "Tutup Log" else "Log Enjin",
                                color = Win11TextPrimary,
                                fontSize = 11.sp
                            )
                        }

                        // Close App Button
                        IconButton(
                            onClick = onClose,
                            modifier = Modifier
                                .size(34.dp)
                                .background(Color(0xFFE81123).copy(alpha = 0.8f), RoundedCornerShape(6.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Runtime",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // Control Mode Overlay
            if (config.controlMode.contains("x86", ignoreCase = true)) {
                // Virtual x86 Trackpad & Mouse Cursor overlay
                VirtualX86TrackpadOverlay(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp)
                )
            } else {
                // ARM Touch Mode banner
                Surface(
                    color = Win11SurfaceCard.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp)
                ) {
                    Text(
                        text = "ARM Touch Mode Aktif: Kawalan Skrin Sentuh Tanpa Kursor (Zero Input Lag)",
                        color = Win11TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }

            // Engine Log Drawer
            AnimatedVisibility(
                visible = showLogs,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(420.dp)
                    .fillMaxSize()
                    .padding(top = 70.dp, bottom = 20.dp, end = 16.dp)
            ) {
                Surface(
                    color = Win11Surface.copy(alpha = 0.96f),
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Win11Border)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Log Pelaksanaan Enjin Hibrid",
                            color = Win11TextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Box64 + DXVK 11 translation events & Roblox memory protection",
                            color = Win11TextSecondary,
                            fontSize = 11.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(Win11Background, RoundedCornerShape(6.dp))
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(logs) { logLine ->
                                Text(
                                    text = logLine,
                                    color = if (logLine.contains("ACTIVE") || logLine.contains("Ready")) Win11Success
                                    else if (logLine.contains("fallback") || logLine.contains("WARN")) Color(0xFFFCE100)
                                    else Win11TextSecondary,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusChip(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, color: Color) {
    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(6.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.4f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun RobloxSimulatedCanvas(
    packageName: String,
    graphicsApi: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw a stylish perspective grid simulating 3D Roblox Workspace
            val gridSpacing = 40f
            val strokeCol = Color(0xFF283447).copy(alpha = 0.5f)
            for (x in 0..size.width.toInt() step gridSpacing.toInt()) {
                drawLine(
                    color = strokeCol,
                    start = Offset(x.toFloat(), 0f),
                    end = Offset(x.toFloat(), size.height),
                    strokeWidth = 1f
                )
            }
            for (y in 0..size.height.toInt() step gridSpacing.toInt()) {
                drawLine(
                    color = strokeCol,
                    start = Offset(0f, y.toFloat()),
                    end = Offset(size.width, y.toFloat()),
                    strokeWidth = 1f
                )
            }

            // Draw simulated 3D platform / baseplate
            val center = Offset(size.width / 2f, size.height / 2f)
            drawRoundRect(
                color = Color(0xFF1E6C41).copy(alpha = 0.3f),
                topLeft = Offset(center.x - 240f, center.y - 100f),
                size = Size(480f, 200f),
                cornerRadius = CornerRadius(16f, 16f)
            )

            // Center Roblox anchor
            drawCircle(
                color = Color(0xFF0078D4),
                radius = 32f,
                center = center
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = packageName.uppercase(),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Rendering via $graphicsApi • 16:9 Immersive Fullscreen Active",
                color = Win11Blue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                color = Win11SurfaceCard.copy(alpha = 0.8f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Roblox Box64 x64 + Touch Movement Mapping + 4GB-8GB OOM Guard : ON",
                    color = Win11Success,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun VirtualX86TrackpadOverlay(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.size(240.dp, 160.dp),
        colors = CardDefaults.cardColors(containerColor = Win11Surface.copy(alpha = 0.95f)),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Win11Border)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Mouse,
                        contentDescription = null,
                        tint = Win11Blue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "x86 Style Trackpad",
                        color = Win11TextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Kursor Aktif",
                    color = Win11Success,
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Trackpad surface
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Win11Background, RoundedCornerShape(8.dp))
                    .border(1.dp, Win11Border.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Kawasan Sentuh Tatal & Kursor",
                    color = Win11TextSecondary,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Left / Right Click Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(Win11SurfaceElevated, RoundedCornerShape(4.dp))
                        .clickable { /* Left Click */ },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Klik Kiri (L)", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(Win11SurfaceElevated, RoundedCornerShape(4.dp))
                        .clickable { /* Right Click */ },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Klik Kanan (R)", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
