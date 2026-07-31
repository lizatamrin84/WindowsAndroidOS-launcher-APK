package com.example.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AndroidGraphicsApiOption
import com.example.model.ApkGraphicsFilterOption
import com.example.model.ApkOsProfileOption
import com.example.model.ArchFilterOption
import com.example.model.ControlModeOption
import com.example.model.EngineConfig
import com.example.model.ExeGraphicsApiOption
import com.example.model.ExeOsProfileOption
import com.example.model.FpsLimitOption
import com.example.model.RamAllowedOption
import com.example.model.VirtualRamOption
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
fun EngineSettingsScreen(
    viewModel: WindowsAndroidOsViewModel
) {
    val config by viewModel.engineConfig.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            HeaderSection(
                title = "Tetapan Enjin WindowsAndroidOS (Aplikasi Tetapan)",
                subtitle = "Ubah suai profil grafik, kawalan, profil OS & pengoptimuman Roblox (Disimpan Automatik)"
            )
        }

        // 1. Android Graphics API (Terjemah kepada Android supaya telefon faham)
        item {
            SettingCardSection(
                title = "1. Terjemah Kepada Android (Bahasa Grafik supaya Telefon Faham)",
                subtitle = "Jika telefon tidak menyokong versi tersebut, ia akan auto-fallback ke Auto tanpa ranap",
                icon = Icons.Default.Tv
            ) {
                ChipSelectorRow(
                    options = AndroidGraphicsApiOption.entries.map { it.label },
                    selected = config.androidGraphicsApi,
                    onSelect = { viewModel.setAndroidGraphicsApi(it) }
                )
            }
        }

        // 2. Control & Input Mode (Cara Menggunakan)
        item {
            SettingCardSection(
                title = "2. Cara Menggunakan (Mod Kawalan Touch & kursor)",
                subtitle = "Arm [default] = Touchscreen laptop style (tanpa kursor). x86 style = Virtual trackpad & kursor",
                icon = Icons.Default.SportsEsports
            ) {
                ChipSelectorRow(
                    options = ControlModeOption.entries.map { it.label },
                    selected = config.controlMode,
                    onSelect = { viewModel.setControlMode(it) }
                )
            }
        }

        // 3. APK OS Profile & EXE OS Profile
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    SettingCardSection(
                        title = "3a. Cara Run/Mod [APK]",
                        subtitle = "Profil Keserasian Android API",
                        icon = Icons.Default.Settings
                    ) {
                        ChipSelectorRow(
                            options = ApkOsProfileOption.entries.map { it.label },
                            selected = config.apkOsProfile,
                            onSelect = { viewModel.setApkOsProfile(it) }
                        )
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    SettingCardSection(
                        title = "3b. Cara Run/Mod [EXE]",
                        subtitle = "Profil Persekitaran Windows OS",
                        icon = Icons.Default.Settings
                    ) {
                        ChipSelectorRow(
                            options = ExeOsProfileOption.entries.map { it.label },
                            selected = config.exeOsProfile,
                            onSelect = { viewModel.setExeOsProfile(it) }
                        )
                    }
                }
            }
        }

        // 4. EXE & APK Graphics API (Faham bahasa aplikasi [EXE] & [APK/XAPK])
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    SettingCardSection(
                        title = "4a. Faham Bahasa Aplikasi [EXE]",
                        subtitle = "Lapisan terjemahan DXVK / 3D biasa / Vulkan",
                        icon = Icons.Default.Tv
                    ) {
                        ChipSelectorRow(
                            options = ExeGraphicsApiOption.entries.map { it.label },
                            selected = config.exeGraphicsApi,
                            onSelect = { viewModel.setExeGraphicsApi(it) }
                        )
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    SettingCardSection(
                        title = "4b. Faham Bahasa Aplikasi [APK/XAPK]",
                        subtitle = "Penapis versi OpenGL ES & Vulkan",
                        icon = Icons.Default.Tv
                    ) {
                        ChipSelectorRow(
                            options = ApkGraphicsFilterOption.entries.map { it.label },
                            selected = config.apkGraphicsFilter,
                            onSelect = { viewModel.setApkGraphicsFilter(it) }
                        )
                    }
                }
            }
        }

        // 5. File Architecture Filter & FPS Cap
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    SettingCardSection(
                        title = "5a. Faham Jenis Fail [XAPK/APK/EXE]",
                        subtitle = "Sokongan seni bina prosesor binaan",
                        icon = Icons.Default.Settings
                    ) {
                        ChipSelectorRow(
                            options = ArchFilterOption.entries.map { it.label },
                            selected = config.archFilter,
                            onSelect = { viewModel.setArchFilter(it) }
                        )
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    SettingCardSection(
                        title = "5b. Had FPS (FPS Cap / Limit)",
                        subtitle = "Kadar bingkai sasaran untuk kelancaran",
                        icon = Icons.Default.Speed
                    ) {
                        ChipSelectorRow(
                            options = FpsLimitOption.entries.map { it.label },
                            selected = config.fpsLimit,
                            onSelect = { viewModel.setFpsLimit(it) }
                        )
                    }
                }
            }
        }

        // 6. RAM Allowed & Virtual RAM (RAM Tambahan)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    SettingCardSection(
                        title = "6a. RAM Allowed (Pengurusan Memori)",
                        subtitle = "Mod peruntukan memori sistem",
                        icon = Icons.Default.Memory
                    ) {
                        ChipSelectorRow(
                            options = RamAllowedOption.entries.map { it.label },
                            selected = config.ramAllowedMode,
                            onSelect = { viewModel.setRamAllowedMode(it) }
                        )
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    SettingCardSection(
                        title = "6b. RAM Virtual (RAM Tambahan)",
                        subtitle = "Swap tambahan untuk mengelak memori tidak cukup",
                        icon = Icons.Default.Memory
                    ) {
                        ChipSelectorRow(
                            options = VirtualRamOption.entries.map { it.label },
                            selected = config.virtualRamSize,
                            onSelect = { viewModel.setVirtualRamSize(it) }
                        )
                    }
                }
            }
        }

        // 7. Special Roblox Studio & Roblox Player Optimizations
        item {
            SettingCardSection(
                title = "7. Pengoptimuman Khas Roblox / Roblox Player / Roblox Studio",
                subtitle = "Tampalan penterjemahan dan penstabilan prestasi pada Android 6.0+ (API 23+)",
                icon = Icons.Default.SportsEsports
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    RobloxToggleRow(
                        title = "Box64 x64 Translation Patch untuk Binari Roblox",
                        description = "Membetulkan serasi pelaksanaan x86_64 Roblox di bawah Box64 tanpa ralat arkib.",
                        checked = config.robloxBox64Patch,
                        onCheckedChange = { viewModel.setRobloxBox64Patch(it) }
                    )
                    RobloxToggleRow(
                        title = "OpenGL/Vulkan Surface Binding Fix (Android 6+)",
                        description = "Memastikan permukaan rendering EGL/Vulkan terikat dengan betul pada Android API 23 ke atas.",
                        checked = config.robloxSurfaceFix,
                        onCheckedChange = { viewModel.setRobloxSurfaceFix(it) }
                    )
                    RobloxToggleRow(
                        title = "Pemetaan Sentuhan Langsung (Zero-Input-Lag Touch to Camera)",
                        description = "Sentuhan jari digandingkan terus kepada kawalan pergerakan dan kamera Roblox tanpa sebarang kelengahan.",
                        checked = config.robloxTouchMapping,
                        onCheckedChange = { viewModel.setRobloxTouchMapping(it) }
                    )
                    RobloxToggleRow(
                        title = "4GB - 8GB RAM Out-of-Memory (OOM) Protection",
                        description = "Mengaktifkan perlindungan halaman swap memori bagi mengelak ranap OOM pada peranti RAM sederhana.",
                        checked = config.robloxOomProtection,
                        onCheckedChange = { viewModel.setRobloxOomProtection(it) }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun HeaderSection(title: String, subtitle: String) {
    Column {
        Text(
            text = title,
            color = Win11TextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = subtitle,
            color = Win11TextSecondary,
            fontSize = 13.sp
        )
    }
}

@Composable
fun SettingCardSection(
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
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Win11Blue,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = title,
                        color = Win11TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = subtitle,
                        color = Win11TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            content()
        }
    }
}

@Composable
fun ChipSelectorRow(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(options) { opt ->
            val isSelected = opt == selected
            FilterChip(
                selected = isSelected,
                onClick = { onSelect(opt) },
                label = {
                    Text(
                        text = opt,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            modifier = Modifier.size(14.dp)
                        )
                    }
                } else null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Win11Blue,
                    selectedLabelColor = Color.White,
                    containerColor = Win11SurfaceElevated,
                    labelColor = Win11TextSecondary
                ),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

@Composable
fun RobloxToggleRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Win11SurfaceElevated, RoundedCornerShape(8.dp))
            .border(1.dp, Win11Border.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .clickable { onCheckedChange(!checked) }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Win11TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                color = Win11TextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Win11Success,
                uncheckedThumbColor = Win11TextSecondary,
                uncheckedTrackColor = Win11SurfaceCard
            )
        )
    }
}
