package com.example.gettingstartedwithjetpackcompose.ota

import retrofit2.http.GET

interface VersionService {
    @GET("version.json")
    suspend fun getVersionInfo(): VersionInfo
}