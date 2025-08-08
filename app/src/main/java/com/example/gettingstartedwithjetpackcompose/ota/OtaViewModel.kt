package com.example.gettingstartedwithjetpackcompose.ota

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtaViewModel @Inject constructor(private val otaRepository: OtaRepository) : ViewModel() {

    private val _versionInfo = MutableStateFlow<VersionInfo?>(null)
    val versionInfo: StateFlow<VersionInfo?> = _versionInfo

    fun checkForUpdate(){
        viewModelScope.launch {
            _versionInfo.value = otaRepository.getLatestVersion()
        }
    }
}