package kg.example.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {

    private lateinit var sharedPreferences: SharedPreferences

    fun unit (context: Context) {
        sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }

    var text : String?
        get() = sharedPreferences.getString("text", "")
        set(value) = sharedPreferences.edit().putString("text", value)!!.apply()

    var onBoardShow: Boolean
        get() = sharedPreferences.getBoolean("onboard", false)
        set(value) = sharedPreferences.edit().putBoolean("onboard", value).apply()

    var swapLayout: Boolean
        get() = sharedPreferences.getBoolean("swapLayout", false)
        set(value) = sharedPreferences.edit().putBoolean("swapLayout", value).apply()

}