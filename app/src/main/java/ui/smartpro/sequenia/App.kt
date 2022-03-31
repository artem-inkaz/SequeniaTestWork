package ui.smartpro.sequenia

import android.app.Application
import android.provider.Settings
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import ui.smartpro.sequenia.debug.ReleaseTree
import ui.smartpro.sequenia.debug.TimberRemoteTree
import ui.smartpro.sequenia.di.appModule
import ui.smartpro.sequenia.model.debug.DeviceDetails

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        initDebugTools()
        startKoin {
            androidContext(this@App)
            modules(
                arrayListOf(
                    appModule
                )
            )
        }
    }

    private fun initDebugTools() {
        if (BuildConfig.DEBUG) {
            val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            val deviceDetails = DeviceDetails(deviceId)
            val remoteTree = TimberRemoteTree(deviceDetails)

            Timber.plant(remoteTree)
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}