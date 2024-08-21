package kg.example.noteapp

import android.app.Application
import androidx.room.Room
import kg.example.noteapp.data.db.AppDataBase
import kg.example.noteapp.utils.PreferenceHelper

class App : Application() {

    companion object {
        var appDataBase: AppDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)
        getInstanse()
    }

    private fun getInstanse(): AppDataBase? {
        if (appDataBase == null) {
            appDataBase = applicationContext?.let {
                Room.databaseBuilder(
                    it,
                    AppDataBase::class.java,
                    "note.database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
        }
        return appDataBase
    }


}