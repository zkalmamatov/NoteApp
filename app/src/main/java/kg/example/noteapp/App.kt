package kg.example.noteapp

import android.app.Application
import kg.example.noteapp.utils.PreferenceHelper

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceHelper ()
        sharedPreferences.unit(this)
    }

}