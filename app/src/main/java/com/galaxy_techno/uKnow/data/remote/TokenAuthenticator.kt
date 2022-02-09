package com.galaxy_techno.uKnow.data.remote

import com.galaxy_techno.uKnow.common.Endpoint
import com.galaxy_techno.uKnow.di.Qualifier
import com.galaxy_techno.uKnow.domain.AppRepository
import com.galaxy_techno.uKnow.presentation.extension.RemoteEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    @Qualifier.RepoScope private val repoScope: CoroutineScope
) : Authenticator {

    companion object {
        private const val FAIL_TOKEN_CODE = 401
        private const val SUCCESS_TOKEN_CODE = 200

    }

    @Inject
    lateinit var appRepository: AppRepository

    override fun authenticate(
        route: Route?,
        response: Response
    ): Request {

        Timber.tag("k1for0").d(response.toString())

        var accessToken: String? = null
        var refreshToken: String? = null

        repoScope.launch {
            appRepository.getAccessToken().collect {
                accessToken = it
            }
            appRepository.getRefreshToken().collect {
                refreshToken = it
            }

            refreshToken?.let { refreshToken ->

                appRepository.fetchRefreshToken(refreshToken).collect {

                    val tokenData = it.data?.data

                    when (it) {
                        is RemoteEvent.LoadingEvent -> {
                        }
                        is RemoteEvent.ErrorEvent -> {
                        }
                        is RemoteEvent.FailEvent -> {
                        }
                        is RemoteEvent.SuccessEvent -> {
                            appRepository.saveAccessToken(tokenData?.accessToken!!)
                            appRepository.saveRefreshToken(tokenData.refreshToken)
                        }
                    }
                }
            }
        }

        return when (response.code) {

            SUCCESS_TOKEN_CODE -> {
                response
                    .request
                    .newBuilder()
                    .header(Endpoint.AUTHORIZATION, "Bearer $accessToken")
                    .build()
            }

            FAIL_TOKEN_CODE -> {
                response
                    .request
                    .newBuilder()
                    .header(Endpoint.AUTHORIZATION, "Bearer $accessToken")
                    .build()
            }

            else -> {
                response
                    .request
                    .newBuilder()
                    .header(Endpoint.AUTHORIZATION, "Bearer $accessToken")
                    .build()
            }
        }

    }

}