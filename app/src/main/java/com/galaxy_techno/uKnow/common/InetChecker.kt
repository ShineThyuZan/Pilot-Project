package com.galaxy_techno.uKnow.common

import java.net.InetSocketAddress
import java.net.Socket

object InetChecker {

    fun check(): Boolean {
        return try {
            val socket = Socket()
            socket.connect(InetSocketAddress("8.8.8.8", 53))
            socket.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}