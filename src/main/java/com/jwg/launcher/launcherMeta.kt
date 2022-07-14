package com.jwg.launcher

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

@Serializable
data class LatestVersions(
    val release: String,
    val snapshot: String,
)

enum class VersionType{
    release,
    snapshot,
    old_beta,
    old_alpha,
}

@Serializable
data class Version(
    val id: String,
    val type: VersionType,
    val url: String,
    val time: String,
    val releaseTime: String,
    val sha1: String,
    val complianceLevel: Int,
)

@Serializable
data class VersionManifest(
    val latest: LatestVersions,
    val versions: Array<Version>,
)

const val LAUNCHERMETA_BASE_URL = "https://launchermeta.mojang.com/mc/game/"

var cachedManifest: VersionManifest? = null

fun getVersionManifest(): VersionManifest {
    if (cachedManifest != null) return cachedManifest!!

    val response = URL(LAUNCHERMETA_BASE_URL + "version_manifest_v2.json").readText()
    val manifest = Json.decodeFromString<VersionManifest>(response)

    cachedManifest = manifest
    return manifest
}
