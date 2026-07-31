package com.example.model

data class EngineConfig(
    // Android native translation graphics API
    val androidGraphicsApi: String = "Auto",
    // Control / input mode
    val controlMode: String = "Arm [default]",
    // APK compatibility OS profile
    val apkOsProfile: String = "Android 13[default]",
    // EXE compatibility OS profile
    val exeOsProfile: String = "Windows 11 [default]",
    // EXE graphics translation layer
    val exeGraphicsApi: String = "DXVK 11 [default]",
    // APK graphics filter API
    val apkGraphicsFilter: String = "OpenGL 3.1 ES dan kebawah",
    // Architecture filter
    val archFilter: String = "x86+x64+ARM, Semua jenis [default]",
    // FPS Cap
    val fpsLimit: String = "15FPS [default]",
    // RAM Allowed
    val ramAllowedMode: String = "Berdasarkan sistem + optimum[default]",
    // Virtual RAM (swap / extra RAM)
    val virtualRamSize: String = "0.05GB [default]",
    // Roblox / Roblox Player / Roblox Studio special optimizations
    val robloxBox64Patch: Boolean = true,
    val robloxSurfaceFix: Boolean = true,
    val robloxTouchMapping: Boolean = true,
    val robloxOomProtection: Boolean = true,
    // First launch OS permission granted
    val osPermissionGranted: Boolean = false
)

enum class AndroidGraphicsApiOption(val label: String, val apiVersion: String) {
    AUTO("Auto (Fallback Default)", "Auto"),
    GLES_30("OpenGL 3.0 ES", "OpenGL 3.0 ES"),
    GLES_31("OpenGL 3.1 ES", "OpenGL 3.1 ES"),
    VULKAN_10("Vulkan 1.0", "Vulkan 1.0"),
    VULKAN_11("Vulkan 1.1", "Vulkan 1.1"),
    VULKAN_12("Vulkan 1.2", "Vulkan 1.2"),
    VULKAN_13("Vulkan 1.3", "Vulkan 1.3"),
    VULKAN_14("Vulkan 1.4", "Vulkan 1.4")
}

enum class ControlModeOption(val label: String, val description: String) {
    ARM_TOUCH("Arm [default]", "Macam guna Laptop touch screen (tak perlu mouse/anak panah untuk tekan)"),
    X86_STYLE("x86 style", "Virtual trackpad & kursor tetikus dengan butang klik kiri/kanan & tatal")
}

enum class ApkOsProfileOption(val label: String) {
    ANDROID_8("Android 8"),
    ANDROID_9("Android 9"),
    ANDROID_10("Android 10"),
    ANDROID_11("Android 11"),
    ANDROID_12("Android 12"),
    ANDROID_13("Android 13[default]"),
    ANDROID_14("Android 14"),
    ANDROID_15("Android 15"),
    ANDROID_16("Android 16")
}

enum class ExeOsProfileOption(val label: String) {
    WIN_7("Windows 7"),
    WIN_8("Windows 8"),
    WIN_8_1("Windows 8.1"),
    WIN_10("Windows 10"),
    WIN_11("Windows 11 [default]")
}

enum class ExeGraphicsApiOption(val label: String, val details: String) {
    DXVK_11("DXVK 11 [default]", "DirectX 11 to Vulkan wrapper"),
    D3D_BIASA("3D biasa", "Direct3D to native OpenGL ES translation"),
    DXVK_10("DXVK 10", "DirectX 10 to Vulkan wrapper"),
    DXVK_9("DXVK 9", "DirectX 9 to Vulkan wrapper"),
    DXVK_8("DXVK 8", "DirectX 8 legacy wrapper"),
    VULKAN("Vulkan", "Direct Native Vulkan pass-through")
}

enum class ApkGraphicsFilterOption(val label: String) {
    GLES_30_DOWN("OpenGL 3.0 ES dan kebawah"),
    GLES_31_DOWN("OpenGL 3.1 ES dan kebawah"),
    VULKAN_10_DOWN("Vulkan 1.0 dan kebawah"),
    VULKAN_12_DOWN("Vulkan 1.2 dan kebawah"),
    VULKAN_14_DOWN("Vulkan 1.4 dan kebawah")
}

enum class ArchFilterOption(val label: String) {
    ALL_DEFAULT("x86+x64+ARM, Semua jenis [default]"),
    ARM64_ONLY("ARM64 / AArch64 Sahaja"),
    X86_64_BOX("x86_64 Box64 Dynamic Translation")
}

enum class FpsLimitOption(val label: String, val numericValue: Int) {
    MAX("MAX", 999),
    CUSTOM("Custom", 60),
    FPS_120("120FPS", 120),
    FPS_60("60FPS", 60),
    FPS_30("30FPS", 30),
    FPS_24("24FPS", 24),
    FPS_20("20FPS", 20),
    FPS_15("15FPS [default]", 15),
    FPS_12("12FPS", 12),
    FPS_8("8FPS", 8)
}

enum class RamAllowedOption(val label: String) {
    SYSTEM_OPTIMUM("Berdasarkan sistem + optimum[default]"),
    SYSTEM_ONLY("Berdasarkan sistem")
}

enum class VirtualRamOption(val label: String, val sizeMb: Int) {
    OFF("off", 0),
    CUSTOM("custom", 512),
    VRAM_0_05("0.05GB [default]", 50),
    VRAM_0_1("0.1GB", 100),
    VRAM_0_5("0.5GB", 500),
    VRAM_0_8("0.8GB", 800),
    VRAM_1("1GB", 1024),
    VRAM_1_5("1.5GB", 1536),
    VRAM_2("2GB", 2048),
    VRAM_2_5("2.5GB", 2560),
    VRAM_3("3GB", 3072),
    VRAM_4("4GB", 4096),
    VRAM_5("5GB", 5120),
    VRAM_6("6GB", 6144)
}

data class InstalledPackage(
    val id: String,
    val name: String,
    val type: PackageType, // APK, XAPK, EXE, STORE
    val version: String,
    val arch: String,
    val size: String,
    val iconRes: Int,
    val isStoreApp: Boolean = false,
    val storeHasPrivileges: Boolean = false,
    val optimizationProfile: String = "Standard"
)

enum class PackageType(val label: String, val badgeColor: Long) {
    APK("APK", 0xFF107C41),
    XAPK("XAPK", 0xFF0078D4),
    EXE("EXE Windows", 0xFF00A4EF),
    STORE("App Store", 0xFF8A2BE2)
}
