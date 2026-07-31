package com.example.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Fluent Windows 11 Mica & Acrylic Palette
val Win11Blue = Color(0xFF0067C0)
val Win11BlueHover = Color(0xFF1883E6)
val Win11Background = Color(0xFF191D23)
val Win11Surface = Color(0xFF20242B)
val Win11SurfaceElevated = Color(0xFF282D37)
val Win11SurfaceCard = Color(0xFF2C323D)
val Win11Border = Color(0xFF38404E)
val Win11TextPrimary = Color(0xFFFFFFFF)
val Win11TextSecondary = Color(0xFFB0B8C4)
val Win11Success = Color(0xFF107C41)
val Win11Warning = Color(0xFFFCE100)
val Win11Error = Color(0xFFE81123)

val Windows11DarkColorScheme = darkColorScheme(
    primary = Win11Blue,
    onPrimary = Color.White,
    secondary = Win11SurfaceCard,
    onSecondary = Win11TextPrimary,
    background = Win11Background,
    onBackground = Win11TextPrimary,
    surface = Win11Surface,
    onSurface = Win11TextPrimary,
    surfaceVariant = Win11SurfaceElevated,
    onSurfaceVariant = Win11TextSecondary,
    outline = Win11Border
)

// Windows 11 Mica surface background Modifier
fun Modifier.micaSurface(
    cornerRadius: Dp = 10.dp,
    borderColor: Color = Win11Border,
    borderWidth: Dp = 1.dp
): Modifier = composed {
    this
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Win11SurfaceElevated.copy(alpha = 0.94f),
                    Win11Surface.copy(alpha = 0.98f)
                )
            ),
            shape = RoundedCornerShape(cornerRadius)
        )
        .border(
            width = borderWidth,
            color = borderColor,
            shape = RoundedCornerShape(cornerRadius)
        )
}
