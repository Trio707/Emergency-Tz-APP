# ProGuard rules for Emergency Tanzania

# Keep Compose Multiplatform classes
-keep class org.jetbrains.compose.** { *; }
-keep class androidx.compose.** { *; }

# Keep Kotlin serialization
-keep class kotlinx.serialization.** { *; }

# Keep our app classes
-keep class tz.emergency.app.** { *; }

# Keep enum values
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Allow obfuscation of everything else