package com.galaxy_techno.uKnow.common

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object UnAvailable : NetworkStatus()
}
