package com.galaxy_techno.uKnow.di

import com.galaxy_techno.uKnow.common.Endpoint
import com.galaxy_techno.uKnow.data.remote.ApiDataSource
import com.galaxy_techno.uKnow.data.remote.ApiDataSourceImpl
import com.galaxy_techno.uKnow.data.remote.ApiService
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiDataSource(apiService: ApiService): ApiDataSource {
        return ApiDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Endpoint.API_HOST)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Qualifier.RepoScope repoScope: CoroutineScope
    ): OkHttpClient {

        //todo : retry limit

        return OkHttpClient.Builder()
            .addInterceptor(OkHttpProfilerInterceptor())
            /*.addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.HEADERS)
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .authenticator(TokenAuthenticator(repoScope))*/
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()

    }


}