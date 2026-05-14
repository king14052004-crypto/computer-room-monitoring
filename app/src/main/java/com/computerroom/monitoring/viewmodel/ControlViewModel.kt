package com.computerroom.monitoring.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.computerroom.monitoring.data.model.DeviceStatus
import com.computerroom.monitoring.data.repository.FirebaseRepository

class ControlViewModel : ViewModel() {

    private val repository = FirebaseRepository.getInstance()

    val deviceStatus: LiveData<DeviceStatus> = repository.deviceStatus
    val error: LiveData<String> = repository.error

    init {
        repository.startListeningDeviceStatus()
    }

    fun toggleFan(on: Boolean) {
        repository.setDeviceFan(on)
    }

    fun toggleLight(on: Boolean) {
        repository.setDeviceLight(on)
    }

    fun toggleBuzzer(on: Boolean) {
        repository.setDeviceBuzzer(on)
    }
}
