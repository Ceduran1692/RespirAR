package ar.edu.ort.respirar

import android.app.Application
import android.util.Log
import ar.edu.ort.respirar.core.Config
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppContext: Application() {
    override fun onCreate() {
        super.onCreate()

        Config.orionUrl= resources.getString(R.string.orion_url)


        Log.i("AppContext","cargando datos:" +
                " orionUrl: "+Config.orionUrl)
    }
}