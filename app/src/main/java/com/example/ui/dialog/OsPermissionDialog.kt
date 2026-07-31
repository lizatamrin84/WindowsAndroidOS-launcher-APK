package com.example.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.Win11Blue
import com.example.ui.theme.Win11Border
import com.example.ui.theme.Win11Surface
import com.example.ui.theme.Win11SurfaceElevated
import com.example.ui.theme.Win11TextPrimary
import com.example.ui.theme.Win11TextSecondary

@Composable
fun OsPermissionDialog(
    onAllow: () -> Unit,
    onReject: () -> Unit
) {
    Dialog(
        onDismissRequest = { /* Require explicit choice */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .width(460.dp)
                .clip(RoundedCornerShape(14.dp))
                .border(1.dp, Win11Border, RoundedCornerShape(14.dp)),
            color = Win11Surface,
            shadowElevation = 24.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                color = Win11Blue.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Windows Security Shield",
                            tint = Win11Blue,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = "Kawalan Kebenaran Sistem OS",
                            color = Win11TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "WindowsAndroidOS Security Guard • API 23+",
                            color = Win11TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Win11SurfaceElevated, RoundedCornerShape(8.dp))
                        .border(1.dp, Win11Border.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Sistem OS bagi tahu bagi tak kebenaran untuk aplikasi ini?, nama aplikasi ini adalah WindowsAndroidOS",
                        color = Win11TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "• Membenarkan pelaksanaan fail binaan Windows (.exe) & Android (.apk / .xapk)\n" +
                            "• Akses ke terjemahan grafik OpenGL ES & Vulkan / DXVK 11 secara langsung\n" +
                            "• Pemasangan & kemas kini automatik aplikasi daripada kedai (Play Store / Microsoft Store)",
                    color = Win11TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onReject,
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text("Tolak", color = Win11TextSecondary)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = onAllow,
                        colors = ButtonDefaults.buttonColors(containerColor = Win11Blue),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text("Benarkan Kebenaran OS", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
