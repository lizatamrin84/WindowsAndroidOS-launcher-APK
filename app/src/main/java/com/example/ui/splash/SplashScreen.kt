package com.example.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Win11Background
import com.example.ui.theme.Win11Blue
import com.example.ui.theme.Win11TextPrimary
import com.example.ui.theme.Win11TextSecondary
import kotlinx.coroutines.delay
import kotlin.math.sin

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }
    var countdownSec by remember { mutableStateOf(5) }
    var statusText by remember { mutableStateOf("Initializing Box64 & DXVK Translation Layers...") }

    // Exactly 5 seconds splash timer
    LaunchedEffect(Unit) {
        delay(1000)
        countdownSec = 4
        statusText = "Translating Android Graphics (OpenGL / Vulkan fallback)..."
        delay(1000)
        countdownSec = 3
        statusText = "Loading Windows 11 Fluent Desktop Shell & x64 Emulation..."
        delay(1000)
        countdownSec = 2
        statusText = "Optimizing Roblox Studio & Roblox Player Engine Profile..."
        delay(1000)
        countdownSec = 1
        statusText = "WindowsAndroidOS Hybrid Runtime Ready. Launching..."
        delay(1000)
        isVisible = false
        delay(300) // Allow smooth fade transition
        onSplashFinished()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(tween(400)),
        exit = fadeOut(tween(400))
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Win11Background
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF1E2838),
                                Win11Background
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Windows 11 + Android Bugdroid side-by-side logo
                    HybridWinAndroidLogo(modifier = Modifier.size(240.dp, 100.dp))

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "WindowsAndroidOS",
                        color = Win11TextPrimary,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )

                    Text(
                        text = "Windows 11 Fluent + Android Hybrid Runtime Engine (16:9 Fullscreen)",
                        color = Win11TextSecondary,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    // Windows 11 signature horizontal spinning dots loading animation
                    Windows11DotLoadingAnimation()

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = statusText,
                        color = Win11Blue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Mula dalam $countdownSec saat...",
                        color = Win11TextSecondary.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "v11.4 Box64+DXVK Hybrid LTS • API Level 23+ (Android 6.0+)",
                        color = Win11TextSecondary.copy(alpha = 0.5f),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Developer : Ammar Arif + Gemini Google AI Studio",
                        color = Win11TextSecondary.copy(alpha = 0.5f),
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun HybridWinAndroidLogo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Windows 11 Logo (4 rounded squares)
        Canvas(modifier = Modifier.size(72.dp)) {
            val gap = 5f
            val sizeSq = (size.width - gap) / 2f
            val rad = CornerRadius(8f, 8f)

            // Top-Left
            drawRoundRect(
                color = Color(0xFF00A4EF),
                topLeft = Offset(0f, 0f),
                size = Size(sizeSq, sizeSq),
                cornerRadius = rad
            )
            // Top-Right
            drawRoundRect(
                color = Color(0xFF0078D4),
                topLeft = Offset(sizeSq + gap, 0f),
                size = Size(sizeSq, sizeSq),
                cornerRadius = rad
            )
            // Bottom-Left
            drawRoundRect(
                color = Color(0xFF0067C0),
                topLeft = Offset(0f, sizeSq + gap),
                size = Size(sizeSq, sizeSq),
                cornerRadius = rad
            )
            // Bottom-Right
            drawRoundRect(
                color = Color(0xFF1883E6),
                topLeft = Offset(sizeSq + gap, sizeSq + gap),
                size = Size(sizeSq, sizeSq),
                cornerRadius = rad
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = "+",
            color = Win11TextSecondary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.width(24.dp))

        // Android Bugdroid Logo
        Canvas(modifier = Modifier.size(72.dp)) {
            val androidGreen = Color(0xFF3DDC84)
            val centerHoriz = size.width / 2f

            // Bugdroid head dome
            drawArc(
                color = androidGreen,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(10f, 10f),
                size = Size(size.width - 20f, size.height - 18f)
            )

            // Eyes
            drawCircle(
                color = Color.White,
                radius = 3.5f,
                center = Offset(centerHoriz - 13f, 32f)
            )
            drawCircle(
                color = Color.White,
                radius = 3.5f,
                center = Offset(centerHoriz + 13f, 32f)
            )

            // Antennas
            drawLine(
                color = androidGreen,
                start = Offset(centerHoriz - 15f, 18f),
                end = Offset(centerHoriz - 24f, 4f),
                strokeWidth = 5f
            )
            drawLine(
                color = androidGreen,
                start = Offset(centerHoriz + 15f, 18f),
                end = Offset(centerHoriz + 24f, 4f),
                strokeWidth = 5f
            )

            // Body bottom accent
            drawRoundRect(
                color = androidGreen,
                topLeft = Offset(10f, size.height / 2f + 10f),
                size = Size(size.width - 20f, size.height / 3f),
                cornerRadius = CornerRadius(6f, 6f)
            )
        }
    }
}

@Composable
fun Windows11DotLoadingAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "win11_dots")
    val animProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )

    Canvas(modifier = Modifier.size(160.dp, 16.dp)) {
        val dotCount = 5
        val baseRadius = 5f
        for (i in 0 until dotCount) {
            val offsetDelay = i * 0.12f
            val dotProgress = ((animProgress - offsetDelay) + 1f) % 1f

            // Smooth oscillating movement across horizontal space
            val xPos = (size.width * sin(dotProgress * Math.PI)).toFloat()
            val alphaVal = sin(dotProgress * Math.PI).toFloat().coerceIn(0.15f, 1f)

            drawCircle(
                color = Win11Blue.copy(alpha = alphaVal),
                radius = baseRadius * alphaVal,
                center = Offset(x = xPos, y = size.height / 2f)
            )
        }
    }
}
