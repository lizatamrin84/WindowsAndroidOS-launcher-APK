package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.EngineConfig
import com.example.model.InstalledPackage
import com.example.model.PackageType
import com.example.nativeengine.NativeRuntimeBridge
import com.example.repository.EngineSettingsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WindowsAndroidOsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EngineSettingsRepository(application)

    val engineConfig: StateFlow<EngineConfig> = repository.engineConfigFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = EngineConfig()
        )

    private val _installedPackages = MutableStateFlow<List<InstalledPackage>>(emptyList())
    val installedPackages: StateFlow<List<InstalledPackage>> = _installedPackages.asStateFlow()

    private val _activeRuntimePackage = MutableStateFlow<InstalledPackage?>(null)
    val activeRuntimePackage: StateFlow<InstalledPackage?> = _activeRuntimePackage.asStateFlow()

    private val _runtimeLog = MutableStateFlow<List<String>>(emptyList())
    val runtimeLog: StateFlow<List<String>> = _runtimeLog.asStateFlow()

    private val _currentFps = MutableStateFlow(15)
    val currentFps: StateFlow<Int> = _currentFps.asStateFlow()

    private val _showPermissionDialog = MutableStateFlow(false)
    val showPermissionDialog: StateFlow<Boolean> = _showPermissionDialog.asStateFlow()

    private val _storePrivilegeActive = MutableStateFlow(true)
    val storePrivilegeActive: StateFlow<Boolean> = _storePrivilegeActive.asStateFlow()

    init {
        loadDefaultPackages()
        viewModelScope.launch {
            // Check permission dialog state
            engineConfig.collect { config ->
                if (!config.osPermissionGranted) {
                    _showPermissionDialog.value = true
                }
            }
        }
    }

    private fun loadDefaultPackages() {
        val list = listOf(
            InstalledPackage(
                id = "roblox_player",
                name = "Roblox Player (Box64 + DXVK 11)",
                type = PackageType.EXE,
                version = "v2.645.0 (Windows/x64)",
                arch = "x86_64 -> ARM64 (Box64)",
                size = "412 MB",
                iconRes = 0,
                optimizationProfile = "Roblox High-Performance Profile"
            ),
            InstalledPackage(
                id = "roblox_studio",
                name = "Roblox Studio 2026 (Windows Native Studio)",
                type = PackageType.EXE,
                version = "v1.54.0 (x64 Win11)",
                arch = "x86_64 -> ARM64 (Box64)",
                size = "860 MB",
                iconRes = 0,
                optimizationProfile = "Roblox Studio DXVK 11 + Memory Guard"
            ),
            InstalledPackage(
                id = "google_play_store",
                name = "Google Play Store (Hybrid Store App)",
                type = PackageType.STORE,
                version = "42.5.18-31 [0] [PR]",
                arch = "x86+x64+ARM, Semua jenis [default]",
                size = "64 MB",
                iconRes = 0,
                isStoreApp = true,
                storeHasPrivileges = true,
                optimizationProfile = "Store Auto-Installer & Dependency Updater"
            ),
            InstalledPackage(
                id = "microsoft_store",
                name = "Microsoft Store (Win11 Executable Store)",
                type = PackageType.STORE,
                version = "22405.1401.5.0",
                arch = "x86+x64+ARM, Semua jenis [default]",
                size = "118 MB",
                iconRes = 0,
                isStoreApp = true,
                storeHasPrivileges = true,
                optimizationProfile = "MS Store Direct Package Manager"
            ),
            InstalledPackage(
                id = "roblox_apk",
                name = "Roblox Mobile Client (Android Native)",
                type = PackageType.APK,
                version = "v2.645 Mobile (Vulkan 1.2)",
                arch = "ARM64-v8a",
                size = "185 MB",
                iconRes = 0,
                optimizationProfile = "Android Native Vulkan Fast-Path"
            ),
            InstalledPackage(
                id = "system_explorer",
                name = "Windows 11 Explorer.exe",
                type = PackageType.EXE,
                version = "10.0.22631.3296",
                arch = "x64 Box64 Bridge",
                size = "45 MB",
                iconRes = 0,
                optimizationProfile = "Fluent Mica Desktop Shell"
            )
        )
        _installedPackages.value = list
    }

    fun grantOsPermission() {
        viewModelScope.launch {
            repository.updateOsPermissionGranted(true)
            _showPermissionDialog.value = false
            appendLog("SYSTEM", "Sistem OS: Kebenaran untuk aplikasi WindowsAndroidOS telah DIBENARKAN oleh pengguna.")
        }
    }

    fun rejectOsPermission() {
        _showPermissionDialog.value = false
        appendLog("SYSTEM", "Sistem OS: Kebenaran ditolak sementara.")
    }

    fun toggleStorePrivilege(enabled: Boolean) {
        _storePrivilegeActive.value = enabled
        appendLog(
            "STORE_SECURITY",
            if (enabled) "Kebenaran kedai aplikasi (Google Play / Microsoft Store): AUTO INSTALL & UPDATE DIAKTIFKAN."
            else "Kebenaran kedai aplikasi DITUTUP."
        )
    }

    fun onDragAndDropPackage(fileName: String) {
        viewModelScope.launch {
            val type = when {
                fileName.endsWith(".apk", ignoreCase = true) -> PackageType.APK
                fileName.endsWith(".xapk", ignoreCase = true) -> PackageType.XAPK
                fileName.endsWith(".exe", ignoreCase = true) -> PackageType.EXE
                fileName.contains("store", ignoreCase = true) -> PackageType.STORE
                else -> PackageType.APK
            }
            val cleanName = fileName.substringBeforeLast(".")
            val isStore = type == PackageType.STORE || cleanName.contains("store", ignoreCase = true)

            val newPackage = InstalledPackage(
                id = "pkg_${System.currentTimeMillis()}",
                name = "$cleanName (${type.label})",
                type = type,
                version = "v1.0.0 (Imported)",
                arch = engineConfig.value.archFilter,
                size = "128 MB",
                iconRes = 0,
                isStoreApp = isStore,
                storeHasPrivileges = isStore && _storePrivilegeActive.value,
                optimizationProfile = if (cleanName.contains("roblox", ignoreCase = true)) {
                    "Roblox Hybrid High-Performance Profile"
                } else "WindowsAndroidOS Auto-Optimize"
            )

            val current = _installedPackages.value.toMutableList()
            current.add(0, newPackage)
            _installedPackages.value = current

            appendLog(
                "INSTALLER",
                "Fail $fileName berjaya diimport ke WindowsAndroidOS!"
            )
            if (isStore) {
                appendLog(
                    "STORE_PRIVILEGE",
                    "Aplikasi Store dikesan: Kebenaran auto-install aplikasi lain & kemas kini automatik diaktifkan untuk $cleanName."
                )
            }
        }
    }

    fun launchPackage(pkg: InstalledPackage) {
        _activeRuntimePackage.value = pkg
        val config = engineConfig.value

        // Parse target FPS limit
        val targetFps = when (config.fpsLimit) {
            "MAX" -> 120
            "120FPS" -> 120
            "60FPS" -> 60
            "30FPS" -> 30
            "24FPS" -> 24
            "20FPS" -> 20
            "15FPS [default]" -> 15
            "12FPS" -> 12
            "8FPS" -> 8
            else -> 15
        }
        _currentFps.value = targetFps

        // Check if graphics API supported on device, fallback to Auto if not
        val apiSupported = NativeRuntimeBridge.checkGraphicsApiSupport(config.androidGraphicsApi)
        val activeAndroidApi = if (apiSupported) config.androidGraphicsApi else "Auto (System Fallback)"

        // Apply Roblox patches if launching Roblox
        val isRoblox = pkg.name.contains("Roblox", ignoreCase = true)
        val patchReport = if (isRoblox) {
            NativeRuntimeBridge.applyRobloxPatches(
                enableBox64x64Patch = config.robloxBox64Patch,
                enableSurfaceFix = config.robloxSurfaceFix,
                enableTouchMapping = config.robloxTouchMapping,
                enableOomProtect = config.robloxOomProtection
            )
        } else ""

        _runtimeLog.value = listOf(
            "============================================================",
            "  WindowsAndroidOS HYBRID RUNTIME ENGINE v11.4-LTS ACTIVE",
            "============================================================",
            "Executing target: ${pkg.name} [Type: ${pkg.type.label}]",
            "Kernel API: ${NativeRuntimeBridge.getKernelVersion()}",
            "Android Graphics (Terjemah Bahasa): $activeAndroidApi",
            "Control / Input Mode: ${config.controlMode}",
            "APK OS Compatibility Profile: ${config.apkOsProfile}",
            "EXE OS Environment Profile: ${config.exeOsProfile}",
            "EXE Graphics Translation Layer: ${config.exeGraphicsApi}",
            "APK Graphics Filter: ${config.apkGraphicsFilter}",
            "Architecture Filter: ${config.archFilter}",
            "FPS Target Cap: ${config.fpsLimit} (${targetFps} FPS Locked)",
            "RAM Allowed: ${config.ramAllowedMode} | Virtual RAM: +${config.virtualRamSize}",
            "============================================================"
        ) + if (patchReport.isNotEmpty()) patchReport.lines().filter { it.isNotBlank() } else emptyList()
    }

    fun closeRuntime() {
        _activeRuntimePackage.value = null
    }

    private fun appendLog(category: String, message: String) {
        val timestamp = android.text.format.DateFormat.format("kk:mm:ss", System.currentTimeMillis())
        val entry = "[$timestamp][$category] $message"
        _runtimeLog.value = _runtimeLog.value + entry
    }

    // Settings update methods delegating to repository
    fun setAndroidGraphicsApi(api: String) = viewModelScope.launch { repository.updateAndroidGraphicsApi(api) }
    fun setControlMode(mode: String) = viewModelScope.launch { repository.updateControlMode(mode) }
    fun setApkOsProfile(profile: String) = viewModelScope.launch { repository.updateApkOsProfile(profile) }
    fun setExeOsProfile(profile: String) = viewModelScope.launch { repository.updateExeOsProfile(profile) }
    fun setExeGraphicsApi(api: String) = viewModelScope.launch { repository.updateExeGraphicsApi(api) }
    fun setApkGraphicsFilter(filter: String) = viewModelScope.launch { repository.updateApkGraphicsFilter(filter) }
    fun setArchFilter(filter: String) = viewModelScope.launch { repository.updateArchFilter(filter) }
    fun setFpsLimit(limit: String) = viewModelScope.launch { repository.updateFpsLimit(limit) }
    fun setRamAllowedMode(mode: String) = viewModelScope.launch { repository.updateRamAllowedMode(mode) }
    fun setVirtualRamSize(size: String) = viewModelScope.launch { repository.updateVirtualRamSize(size) }
    fun setRobloxBox64Patch(enabled: Boolean) = viewModelScope.launch { repository.updateRobloxBox64Patch(enabled) }
    fun setRobloxSurfaceFix(enabled: Boolean) = viewModelScope.launch { repository.updateRobloxSurfaceFix(enabled) }
    fun setRobloxTouchMapping(enabled: Boolean) = viewModelScope.launch { repository.updateRobloxTouchMapping(enabled) }
    fun setRobloxOomProtection(enabled: Boolean) = viewModelScope.launch { repository.updateRobloxOomProtection(enabled) }
}
