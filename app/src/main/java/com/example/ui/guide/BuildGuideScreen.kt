package com.example.ui.guide

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Win11Blue
import com.example.ui.theme.Win11Border
import com.example.ui.theme.Win11Surface
import com.example.ui.theme.Win11SurfaceCard
import com.example.ui.theme.Win11SurfaceElevated
import com.example.ui.theme.Win11TextPrimary
import com.example.ui.theme.Win11TextSecondary

@Composable
fun BuildGuideScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Panduan Membina APK WindowsAndroidOS (Android Studio & Termux/AIDE)",
                    color = Win11TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Langkah demi langkah menyusun projek Kotlin + C++ NDK ini menjadi APK yang siap dipasang",
                    color = Win11TextSecondary,
                    fontSize = 13.sp
                )
            }
        }

        // Section 1: Android Studio Build Guide
        item {
            GuideCard(
                title = "Kaedah 1: Menggunakan Android Studio (PC / Mac / Linux)",
                subtitle = "Sesuai untuk pengubahsuaian penuh termasuk kod C++ NDK CMake",
                icon = Icons.Default.Computer
            ) {
                GuideStepItem(
                    stepNumber = "1",
                    title = "Buka Projek di Android Studio (Iguana / Jellyfish / Ladybug ke atas)",
                    desc = "Pilih 'Open Project' dan pilih direktori utama (root) WindowsAndroidOS. Pastikan SDK Platform Android 36 & NDK (r26+ atau r27+) telah dimuat turun di SDK Manager."
                )
                GuideStepItem(
                    stepNumber = "2",
                    title = "Semak Konfig build.gradle.kts & CMakeLists.txt",
                    desc = "Projek ini disesuaikan untuk API 23 ke atas (Android 6.0+). Jika C++ NDK diperlukan, aktifkan blok externalNativeBuild dalam build.gradle.kts (atau gunakan jambatan Kotlin fallback jika tiada NDK)."
                )
                GuideStepItem(
                    stepNumber = "3",
                    title = "Bina & Compile menjadi APK",
                    desc = "Pada menu atas, klik Build -> Build Bundle(s) / APK(s) -> Build APK(s). Setelah selesai, fail APK akan berada di dalam app/build/outputs/apk/debug/app-debug.apk."
                )
            }
        }

        // Section 2: Termux / AIDE Mobile Build Guide
        item {
            GuideCard(
                title = "Kaedah 2: Menggunakan Termux di Telefon Android",
                subtitle = "Bina APK terus dari telefon menggunakan baris arahan (Terminal CLI)",
                icon = Icons.Default.Terminal
            ) {
                GuideStepItem(
                    stepNumber = "1",
                    title = "Pasang Pakej Wajib di Termux",
                    desc = "Jalankan arahan:\n" +
                            "pkg update && pkg install openjdk-17 gradle git cmake ndk-sysroot"
                )
                GuideStepItem(
                    stepNumber = "2",
                    title = "Klon atau Simpan Folder Projek",
                    desc = "Letakkan semua fail projek ini dalam direktori Termux anda, contohnya ~/WindowsAndroidOS."
                )
                GuideStepItem(
                    stepNumber = "3",
                    title = "Jalankan Arahan Gradle Build",
                    desc = "Masuk ke folder projek dengan 'cd ~/WindowsAndroidOS' dan lancarkan pembinaan APK:\n" +
                            "gradle :app:assembleDebug\n" +
                            "Fail APK yang terhasil boleh dipasang terus ke telefon bimbit anda."
                )
            }
        }

        // Section 3: How Hybrid Roblox Box64 & DXVK Translation Works
        item {
            GuideCard(
                title = "Arsitektur Enjin Hibrid (Box64 + DXVK 11)",
                subtitle = "Bagaimana aplikasi ini menjalankan fail .EXE dan .APK bersama mod kawalan Touch",
                icon = Icons.Default.Info
            ) {
                Text(
                    text = "• Box64 Dynamic Recompiler: Menukarkan arahan binari x86_64 (Windows/Roblox) kepada arahan ARM64 dalam masa nyata tanpa kelengahan.\n" +
                            "• DXVK 11 Lapisan Grafik: Mengalih panggilan DirectX 11 kepada Vulkan 1.0 - 1.4 rasmi telefon, atau fallback kepada OpenGL ES 3.1/3.0 jika GPU tidak menyokong.\n" +
                            "• Pemetaan Sentuhan Langsung (Zero-Lag): Mengelakkan keperluan kursor tetikus dengan menterjemah sentuhan skrin terus kepada pergerakan kamera & kawalan watak Roblox.\n" +
                            "• Perlindungan Memori 4GB-8GB: Menetapkan had halaman swap memori supaya Roblox Player dan Roblox Studio tidak ranap (OOM).",
                    color = Win11TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun GuideCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Win11SurfaceCard),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Win11Blue,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        color = Win11TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        color = Win11TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                content()
            }
        }
    }
}

@Composable
fun GuideStepItem(
    stepNumber: String,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Win11SurfaceElevated, RoundedCornerShape(8.dp))
            .border(1.dp, Win11Border.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .size(26.dp)
                .background(Win11Blue, RoundedCornerShape(6.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stepNumber,
                color = Win11TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = title,
                color = Win11TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = desc,
                color = Win11TextSecondary,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                fontFamily = if (desc.contains("gradle") || desc.contains("pkg")) FontFamily.Monospace else FontFamily.SansSerif
            )
        }
    }
}
