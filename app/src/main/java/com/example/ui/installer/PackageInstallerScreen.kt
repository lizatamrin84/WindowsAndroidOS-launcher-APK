package com.example.ui.installer

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.InstalledPackage
import com.example.model.PackageType
import com.example.ui.theme.Win11Blue
import com.example.ui.theme.Win11Border
import com.example.ui.theme.Win11Success
import com.example.ui.theme.Win11Surface
import com.example.ui.theme.Win11SurfaceCard
import com.example.ui.theme.Win11SurfaceElevated
import com.example.ui.theme.Win11TextPrimary
import com.example.ui.theme.Win11TextSecondary
import com.example.ui.theme.Win11Warning
import com.example.viewmodel.WindowsAndroidOsViewModel

@Composable
fun PackageInstallerScreen(
    viewModel: WindowsAndroidOsViewModel
) {
    val packages by viewModel.installedPackages.collectAsState()
    val storePrivilegeActive by viewModel.storePrivilegeActive.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Pasang & Drag-Drop (.APK, .XAPK, .EXE, & Kedai Aplikasi)",
                    color = Win11TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Import fail binari atau muat turun aplikasi dari kedai. Sokongan hak istimewa kemas kini automatik.",
                    color = Win11TextSecondary,
                    fontSize = 13.sp
                )
            }
        }

        // Store App Privileges Header Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (storePrivilegeActive) Win11Success.copy(alpha = 0.15f) else Win11SurfaceCard
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = if (storePrivilegeActive) Win11Success else Win11Border,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(
                                color = if (storePrivilegeActive) Win11Success.copy(alpha = 0.2f) else Win11Border,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (storePrivilegeActive) Icons.Default.CheckCircle else Icons.Default.Security,
                            contentDescription = null,
                            tint = if (storePrivilegeActive) Win11Success else Win11TextSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Kebenaran Kedai Aplikasi (Google Play Store / Microsoft Store)",
                            color = Win11TextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (storePrivilegeActive) {
                                "AKTIF: Aplikasi Store yang diimport dibenarkan memasang app lain & membuat kemas kini (update) automatik tanpa sekatan."
                            } else {
                                "DITUTUP: Aplikasi dari kedai memerlukan kebenaran pengguna bagi setiap pemasangan."
                            },
                            color = Win11TextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Switch(
                        checked = storePrivilegeActive,
                        onCheckedChange = { viewModel.toggleStorePrivilege(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Win11Success
                        )
                    )
                }
            }
        }

        // Drag & Drop Drop Zone Card (Interactive Simulation)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Win11SurfaceCard),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudDownload,
                        contentDescription = "Drag and drop",
                        tint = Win11Blue,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Seret dan Lepaskan Fail (Drag & Drop) ke dalam Kawasan Ini",
                        color = Win11TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Sokongan fail: .APK, .XAPK, .EXE (Windows), Roblox Installer, dan Google Play / Microsoft Store Installer",
                        color = Win11TextSecondary,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Quick simulation buttons for testing
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { viewModel.onDragAndDropPackage("Roblox_Studio_Installer_2026.exe") },
                            colors = ButtonDefaults.buttonColors(containerColor = Win11Blue),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Simulasi Lepaskan Roblox_Studio.exe", fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = { viewModel.onDragAndDropPackage("Google_Play_Store_v42.apk") },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Simulasi Import Play Store.apk", color = Win11TextPrimary, fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = { viewModel.onDragAndDropPackage("Microsoft_Store_Win11.exe") },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Simulasi Import Microsoft Store.exe", color = Win11TextPrimary, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Installed Package List Section
        item {
            Text(
                text = "Senarai Pakej Terpasang (${packages.size})",
                color = Win11TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(packages) { pkg ->
            InstalledPackageCard(
                pkg = pkg,
                onLaunch = { viewModel.launchPackage(pkg) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun InstalledPackageCard(
    pkg: InstalledPackage,
    onLaunch: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Win11SurfaceCard),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(pkg.type.badgeColor).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (pkg.type) {
                        PackageType.STORE -> Icons.Default.Store
                        PackageType.EXE -> Icons.Default.Apps
                        else -> Icons.Default.Apps
                    },
                    contentDescription = pkg.name,
                    tint = Color(pkg.type.badgeColor),
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = pkg.name,
                        color = Win11TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Surface(
                        color = Color(pkg.type.badgeColor),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = pkg.type.label,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    if (pkg.isStoreApp && pkg.storeHasPrivileges) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = Win11Success,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "PRIVILEGE AUTO-INSTALL",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${pkg.version} • ${pkg.arch} • ${pkg.size}",
                    color = Win11TextSecondary,
                    fontSize = 12.sp
                )

                Text(
                    text = "Profil: ${pkg.optimizationProfile}",
                    color = Win11Blue,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onLaunch,
                colors = ButtonDefaults.buttonColors(containerColor = Win11Blue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Launch",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Jalankan", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }
        }
    }
}
