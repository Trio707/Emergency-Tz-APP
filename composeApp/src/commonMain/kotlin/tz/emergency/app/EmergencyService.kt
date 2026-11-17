package tz.emergency.app

/**
 * Verified mainland Tanzania emergency contacts.
 *
 * Last verified: 2026-09-14
 * Sources:
 * - https://www.tanzania.go.tz/faqs
 * - https://www.polisi.go.tz/mawasiliano/
 */
enum class EmergencyService(
    val displayName: String,
    val phoneNumber: String,
) {
    POLICE(displayName = "Police", phoneNumber = "112"),
    FIRE_AND_RESCUE(displayName = "Fire and Rescue", phoneNumber = "114"),
    AMBULANCE(displayName = "Ambulance", phoneNumber = "115"),
}