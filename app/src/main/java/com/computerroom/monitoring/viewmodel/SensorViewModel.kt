package com.computerroom.monitoring.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.computerroom.monitoring.data.model.SensorData
import com.computerroom.monitoring.data.repository.FirebaseRepository

class SensorViewModel : ViewModel() {

    private val repository = FirebaseRepository.getInstance()

    val sensorData: LiveData<SensorData> = repository.sensorData
    val error: LiveData<String> = repository.error

    init {
        repository.startListeningSensorData()
    }
}
