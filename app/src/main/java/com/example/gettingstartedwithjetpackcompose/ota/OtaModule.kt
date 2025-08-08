package com.example.gettingstartedwithjetpackcompose.ota

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OtaModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/Leilani-K/sample/main/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideVersionService(retrofit: Retrofit): VersionService =
        retrofit.create(VersionService::class.java)

    @Provides
    @Singleton
    fun provideOtaRepository(versionService: VersionService): OtaRepository =
        OtaRepository(versionService)
}
