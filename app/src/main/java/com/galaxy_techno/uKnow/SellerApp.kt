package com.galaxy_techno.uKnow

import androidx.multidex.MultiDexApplication
import com.bumptech.glide.annotation.GlideModule
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@GlideModule
@HiltAndroidApp
class SellerApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}