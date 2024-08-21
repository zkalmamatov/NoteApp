package kg.example.noteapp.converter

import android.graphics.Color
import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromColorString(colorString: String): Int? {
        return colorString?.let {
            Color.parseColor(it)
        }
    }

    @TypeConverter
    fun toColorString(color: Int): String {
        return "#${Integer.toHexString(color)}"
    }
}