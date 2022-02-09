package com.galaxy_techno.uKnow.common

import com.galaxy_techno.uKnow.presentation.extension.RemoteEvent
import retrofit2.Response

open class RemoteDataSource {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): RemoteEvent<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body() ?: return RemoteEvent.ErrorEvent("EMPTY BODY")
                return RemoteEvent.SuccessEvent(body)
            }
            return RemoteEvent.ErrorEvent("ERROR CODE ${response.code()} : ${response.message()}")

        } catch (e: Exception) {
            return RemoteEvent.ErrorEvent(e.message ?: e.toString())
        }

    }
}