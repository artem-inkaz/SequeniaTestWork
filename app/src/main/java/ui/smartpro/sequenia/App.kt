package ui.smartpro.sequenia

import android.app.Application
import android.provider.Settings
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import ui.smartpro.sequenia.data.debug.DeviceDetails
import ui.smartpro.sequenia.debug.ReleaseTree
import ui.smartpro.sequenia.debug.TimberRemoteTree
import ui.smartpro.sequenia.di.analytic
import ui.smartpro.sequenia.di.dataModule
import ui.smartpro.sequenia.di.domainModule
import ui.smartpro.sequenia.di.presentationModule

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        initDebugTools()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                    domainModule, presentationModule, dataModule,analytic
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