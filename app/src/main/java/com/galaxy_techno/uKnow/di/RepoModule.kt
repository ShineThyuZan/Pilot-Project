package com.galaxy_techno.uKnow.di

import com.galaxy_techno.uKnow.data.db.DbDataSource
import com.galaxy_techno.uKnow.data.ds.DsDataSource
import com.galaxy_techno.uKnow.data.remote.ApiDataSource
import com.galaxy_techno.uKnow.data.remote.ApiService
import com.galaxy_techno.uKnow.domain.AppRepository
import com.galaxy_techno.uKnow.domain.AppRepositoryImpl
import com.galaxy_techno.uKnow.domain.UserRepository
import com.galaxy_techno.uKnow.domain.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepoModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        apiDataSource: ApiDataSource,
        dbDataSource: DbDataSource,
        dsDataSource: DsDataSource,
        @Qualifier.Io io: CoroutineDispatcher,
        apiService: ApiService
    ): UserRepository {
        return UserRepositoryImpl(
            apiDataSource,
            dbDataSource,
            dsDataSource,
            io,
            apiService
        )
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        apiDataSource: ApiDataSource,
        dbDataSource: DbDataSource,
        dsDataSource: DsDataSource,
        @Qualifier.Io io: CoroutineDispatcher
    ): AppRepository {
        return AppRepositoryImpl(
            apiDataSource,
            dbDataSource,
            dsDataSource,
            io
        )
    }
}