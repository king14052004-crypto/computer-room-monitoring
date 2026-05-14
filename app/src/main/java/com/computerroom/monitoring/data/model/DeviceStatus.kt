package com.computerroom.monitoring.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DeviceStatus(
    val fan: Boolean = false,
    val light: Boolean = false,
    val buzzer: Boolean = false
) {
    constructor() : this(false, false, false)
}
