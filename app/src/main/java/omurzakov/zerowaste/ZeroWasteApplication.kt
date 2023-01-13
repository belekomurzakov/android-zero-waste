package omurzakov.zerowaste

import android.app.Application
import android.content.Context
import omurzakov.zerowaste.di.*
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class ZeroWasteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ZeroWasteApplication)
            modules(
                listOf(
                    apiModule,
                    daoModule,
                    mlModule,
                    databaseModule,
                    retrofitModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

    companion object {
        lateinit var appContext: Context private set
    }
}