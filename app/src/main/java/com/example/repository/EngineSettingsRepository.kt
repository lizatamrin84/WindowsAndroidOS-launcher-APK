package com.example.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.EngineConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "windowsandroidos_engine_prefs")

class EngineSettingsRepository(private val context: Context) {

    companion object {
        private val ANDROID_GRAPHICS_API = stringPreferencesKey("android_graphics_api")
        private val CONTROL_MODE = stringPreferencesKey("control_mode")
        private val APK_OS_PROFILE = stringPreferencesKey("apk_os_profile")
        private val EXE_OS_PROFILE = stringPreferencesKey("exe_os_profile")
        private val EXE_GRAPHICS_API = stringPreferencesKey("exe_graphics_api")
        private val APK_GRAPHICS_FILTER = stringPreferencesKey("apk_graphics_filter")
        private val ARCH_FILTER = stringPreferencesKey("arch_filter")
        private val FPS_LIMIT = stringPreferencesKey("fps_limit")
        private val RAM_ALLOWED_MODE = stringPreferencesKey("ram_allowed_mode")
        private val VIRTUAL_RAM_SIZE = stringPreferencesKey("virtual_ram_size")
        private val ROBLOX_BOX64_PATCH = booleanPreferencesKey("roblox_box64_patch")
        private val ROBLOX_SURFACE_FIX = booleanPreferencesKey("roblox_surface_fix")
        private val ROBLOX_TOUCH_MAPPING = booleanPreferencesKey("roblox_touch_mapping")
        private val ROBLOX_OOM_PROTECT = booleanPreferencesKey("roblox_oom_protect")
        private val OS_PERMISSION_GRANTED = booleanPreferencesKey("os_permission_granted")
    }

    val engineConfigFlow: Flow<EngineConfig> = context.dataStore.data.map { prefs ->
        EngineConfig(
            androidGraphicsApi = prefs[ANDROID_GRAPHICS_API] ?: "Auto",
            controlMode = prefs[CONTROL_MODE] ?: "Arm [default]",
            apkOsProfile = prefs[APK_OS_PROFILE] ?: "Android 13[default]",
            exeOsProfile = prefs[EXE_OS_PROFILE] ?: "Windows 11 [default]",
            exeGraphicsApi = prefs[EXE_GRAPHICS_API] ?: "DXVK 11 [default]",
            apkGraphicsFilter = prefs[APK_GRAPHICS_FILTER] ?: "OpenGL 3.1 ES dan kebawah",
            archFilter = prefs[ARCH_FILTER] ?: "x86+x64+ARM, Semua jenis [default]",
            fpsLimit = prefs[FPS_LIMIT] ?: "15FPS [default]",
            ramAllowedMode = prefs[RAM_ALLOWED_MODE] ?: "Berdasarkan sistem + optimum[default]",
            virtualRamSize = prefs[VIRTUAL_RAM_SIZE] ?: "0.05GB [default]",
            robloxBox64Patch = prefs[ROBLOX_BOX64_PATCH] ?: true,
            robloxSurfaceFix = prefs[ROBLOX_SURFACE_FIX] ?: true,
            robloxTouchMapping = prefs[ROBLOX_TOUCH_MAPPING] ?: true,
            robloxOomProtection = prefs[ROBLOX_OOM_PROTECT] ?: true,
            osPermissionGranted = prefs[OS_PERMISSION_GRANTED] ?: false
        )
    }

    suspend fun updateAndroidGraphicsApi(api: String) {
        context.dataStore.edit { it[ANDROID_GRAPHICS_API] = api }
    }

    suspend fun updateControlMode(mode: String) {
        context.dataStore.edit { it[CONTROL_MODE] = mode }
    }

    suspend fun updateApkOsProfile(profile: String) {
        context.dataStore.edit { it[APK_OS_PROFILE] = profile }
    }

    suspend fun updateExeOsProfile(profile: String) {
        context.dataStore.edit { it[EXE_OS_PROFILE] = profile }
    }

    suspend fun updateExeGraphicsApi(api: String) {
        context.dataStore.edit { it[EXE_GRAPHICS_API] = api }
    }

    suspend fun updateApkGraphicsFilter(filter: String) {
        context.dataStore.edit { it[APK_GRAPHICS_FILTER] = filter }
    }

    suspend fun updateArchFilter(filter: String) {
        context.dataStore.edit { it[ARCH_FILTER] = filter }
    }

    suspend fun updateFpsLimit(limit: String) {
        context.dataStore.edit { it[FPS_LIMIT] = limit }
    }

    suspend fun updateRamAllowedMode(mode: String) {
        context.dataStore.edit { it[RAM_ALLOWED_MODE] = mode }
    }

    suspend fun updateVirtualRamSize(size: String) {
        context.dataStore.edit { it[VIRTUAL_RAM_SIZE] = size }
    }

    suspend fun updateRobloxBox64Patch(enabled: Boolean) {
        context.dataStore.edit { it[ROBLOX_BOX64_PATCH] = enabled }
    }

    suspend fun updateRobloxSurfaceFix(enabled: Boolean) {
        context.dataStore.edit { it[ROBLOX_SURFACE_FIX] = enabled }
    }

    suspend fun updateRobloxTouchMapping(enabled: Boolean) {
        context.dataStore.edit { it[ROBLOX_TOUCH_MAPPING] = enabled }
    }

    suspend fun updateRobloxOomProtection(enabled: Boolean) {
        context.dataStore.edit { it[ROBLOX_OOM_PROTECT] = enabled }
    }

    suspend fun updateOsPermissionGranted(granted: Boolean) {
        context.dataStore.edit { it[OS_PERMISSION_GRANTED] = granted }
    }
}
