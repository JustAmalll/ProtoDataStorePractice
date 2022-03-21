package dev.amal.protodatastore

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val language: Language = Language.ENGLISH
)

enum class Language {
    ENGLISH, RUSSIAN, GERMAN
}
