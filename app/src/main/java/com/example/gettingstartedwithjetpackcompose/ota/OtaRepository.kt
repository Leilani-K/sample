package com.example.gettingstartedwithjetpackcompose.ota

import javax.inject.Inject

class OtaRepository @Inject constructor(
    private val versionService: VersionService
) {
    suspend fun getLatestVersion(): VersionInfo? {
        return try {
            versionService.getVersionInfo() // Retrofit handles network + parsing
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
