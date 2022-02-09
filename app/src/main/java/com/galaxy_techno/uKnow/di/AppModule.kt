package com.galaxy_techno.uKnow.di

import android.content.Context
import com.galaxy_techno.uKnow.common.InternetChecker
import com.galaxy_techno.uKnow.util.NotificationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ActivityComponent::class)
object AppModule {
    @Provides
    @ActivityScoped
    fun providesNetwork(
        @ApplicationContext context: Context,
        @Qualifier.Io io: CoroutineDispatcher
    ): InternetChecker {
        return InternetChecker(context, io)
    }

    @Provides
    @ActivityScoped
    fun providesNotification(
        @ApplicationContext context: Context
    ): NotificationUtil {
        return NotificationUtil((context))
    }

}