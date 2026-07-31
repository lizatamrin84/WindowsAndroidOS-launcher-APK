package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.InstalledPackage
import com.example.model.PackageType
import com.example.ui.dialog.OsPermissionDialog
import com.example.ui.guide.BuildGuideScreen
import com.example.ui.installer.PackageInstallerScreen
import com.example.ui.runtime.RuntimeScreen
import com.example.ui.settings.EngineSettingsScreen
import com.example.ui.splash.SplashScreen
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

enum class NavigationTab(val label: String, val icon: ImageVector) {
    HOME("Laman Utama & Aplikasi", Icons.Default.Apps),
    INSTALLER("Pasang & Drag-Drop (.APK/.EXE)", Icons.Default.Download),
    SETTINGS("Tetapan Enjin WindowsAndroidOS", Icons.Default.Settings),
    GUIDE("Panduan Bina APK/Termux", Icons.Default.Book)
}

@Composable
fun WindowsAndroidOsApp(
    viewModel: WindowsAndroidOsViewModel
) {
    var isSplashFinished by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(NavigationTab.HOME) }
    val showPermissionDialog by viewModel.showPermissionDialog.collectAsState()
    val activeRuntimePackage by viewModel.activeRuntimePackage.collectAsState()
    val packages by viewModel.installedPackages.collectAsState()
    val config by viewModel.engineConfig.collectAsState()

    // 1. Splash Screen (Exactly 5 seconds)
    if (!isSplashFinished) {
        SplashScreen(onSplashFinished = { isSplashFinished = true })
        return
    }

    // 2. Active Runtime Mode (16:9 Fullscreen Application Execution)
    if (activeRuntimePackage != null) {
        RuntimeScreen(
            pkg = activeRuntimePackage!!,
            viewModel = viewModel,
            onClose = { viewModel.closeRuntime() }
        )
        return
    }

    // 3. Windows 11 Fluent Design Main Desktop Shell
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Win11Background
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Windows 11 Mica Left Navigation Sidebar
            Windows11Sidebar(
                selectedTab = selectedTab,
                onSelectTab = { selectedTab = it },
                kernelVersion = "v11.4 Box64+DXVK",
                activeAndroidApi = config.androidGraphicsApi,
                controlMode = config.controlMode
            )

            // Main Content Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Win11Background)
            ) {
                when (selectedTab) {
                    NavigationTab.HOME -> HomeDashboardContent(
                        packages = packages,
                        onLaunch = { viewModel.launchPackage(it) },
                        onGoToInstaller = { selectedTab = NavigationTab.INSTALLER },
                        onGoToSettings = { selectedTab = NavigationTab.SETTINGS }
                    )
                    NavigationTab.INSTALLER -> PackageInstallerScreen(viewModel = viewModel)
                    NavigationTab.SETTINGS -> EngineSettingsScreen(viewModel = viewModel)
                    NavigationTab.GUIDE -> BuildGuideScreen()
                }
            }
        }

        // 4. OS Permission Modal Prompt (Shown on first open)
        if (showPermissionDialog) {
            OsPermissionDialog(
                onAllow = { viewModel.grantOsPermission() },
                onReject = { viewModel.rejectOsPermission() }
            )
        }
    }
}

@Composable
fun Windows11Sidebar(
    selectedTab: NavigationTab,
    onSelectTab: (NavigationTab) -> Unit,
    kernelVersion: String,
    activeAndroidApi: String,
    controlMode: String
) {
    Surface(
        modifier = Modifier
            .width(260.dp)
            .fillMaxSize(),
        color = Win11Surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, Win11Border)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // App Logo & Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 24.dp, top = 8.dp)
                ) {
                    com.example.ui.splash.HybridWinAndroidLogo(modifier = Modifier.size(60.dp, 28.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "WindowsAndroidOS",
                            color = Win11TextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "16:9 Immersive Shell",
                            color = Win11Blue,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Text(
                    text = "MENU UTAMA",
                    color = Win11TextSecondary.copy(alpha = 0.6f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp, start = 8.dp)
                )

                // Navigation Items
                NavigationTab.entries.forEach { tab ->
                    val isSelected = tab == selectedTab
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .background(
                                color = if (isSelected) Win11Blue.copy(alpha = 0.2f) else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onSelectTab(tab) }
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label,
                            tint = if (isSelected) Win11Blue else Win11TextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = tab.label,
                            color = if (isSelected) Win11TextPrimary else Win11TextSecondary,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            // Bottom Kernel Status Box
            Surface(
                color = Win11SurfaceElevated,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Win11Border.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = Win11Success,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "KERNEL HIBRID SIAP",
                            color = Win11Success,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$kernelVersion\nAPI: $activeAndroidApi\nInput: $controlMode",
                        color = Win11TextSecondary,
                        fontSize = 10.sp,
                        lineHeight = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun HomeDashboardContent(
    packages: List<InstalledPackage>,
    onLaunch: (InstalledPackage) -> Unit,
    onGoToInstaller: () -> Unit,
    onGoToSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Hero Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Laman Utama WindowsAndroidOS",
                    color = Win11TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Lancarkan aplikasi Roblox Studio, Roblox Player, .EXE Windows & .APK Android (Skrin Penuh 16:9)",
                    color = Win11TextSecondary,
                    fontSize = 13.sp
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Surface(
                    color = Win11Blue.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.clickable { onGoToInstaller() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = null,
                            tint = Win11Blue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Pasang / Drag-Drop", color = Win11Blue, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Surface(
                    color = Win11SurfaceCard,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.clickable { onGoToSettings() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = Win11TextPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Tetapan Enjin", color = Win11TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Roblox Special Highlight Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Win11SurfaceCard),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFF0078D4).copy(alpha = 0.2f), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SportsEsports,
                            contentDescription = "Roblox",
                            tint = Color(0xFF0078D4),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Pengoptimuman Roblox Studio & Roblox Player (Aktif)",
                            color = Win11TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "• Box64 x64 Dynamic Translation  • EGL/Vulkan Surface Fix (API 28+)  • Touch Movement Tanpa Lag  • 4GB-8GB OOM Protection",
                            color = Win11TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }

                Surface(
                    color = Win11Success,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "READY FOR ROBLOX",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Pakej Aplikasi & Binari Terpasang",
            color = Win11TextPrimary,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Applications Grid
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(packages) { pkg ->
                AppGridCard(
                    pkg = pkg,
                    onLaunch = { onLaunch(pkg) }
                )
            }
        }
    }
}

@Composable
fun AppGridCard(
    pkg: InstalledPackage,
    onLaunch: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLaunch() },
        colors = CardDefaults.cardColors(containerColor = Win11SurfaceCard),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(pkg.type.badgeColor).copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (pkg.type) {
                                PackageType.STORE -> Icons.Default.Store
                                PackageType.EXE -> Icons.Default.Apps
                                else -> Icons.Default.Apps
                            },
                            contentDescription = null,
                            tint = Color(pkg.type.badgeColor),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = pkg.name,
                            color = Win11TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${pkg.type.label} • ${pkg.version}",
                            color = Win11TextSecondary,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Profil: ${pkg.optimizationProfile}",
                color = Win11Blue,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    color = Win11Blue,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Launch",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Jalankan",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
