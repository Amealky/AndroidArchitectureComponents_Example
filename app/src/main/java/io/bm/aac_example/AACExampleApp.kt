package io.bm.aac_example

import android.app.Activity
import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.bm.aac_example.api.CoinResultDeserializer
import io.bm.aac_example.di.DaggerAppComponent
import io.bm.aac_example.model.Coin
import timber.log.Timber
import javax.inject.Inject


//Classe d'initialisation
class AACExampleApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            initializeDebugConfiguration()
        }

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        super.onCreate()
    }

    private fun initializeDebugConfiguration() {

        //Use chrome://inspect to insepct devices
        Stetho.initializeWithDefaults(this)
        Timber.plant(Timber.DebugTree())
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
            .detectDiskReads()
            .detectDiskWrites()
            .detectAll()
            .penaltyLog()
            .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
            .detectLeakedSqlLiteObjects()
            .detectLeakedClosableObjects()
            .penaltyLog()
            .penaltyDeath()
            .build())
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidActivityInjector
    }
}