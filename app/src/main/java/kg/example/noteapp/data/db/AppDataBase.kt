package kg.example.noteapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kg.example.noteapp.converter.Converters
import kg.example.noteapp.data.db.daos.NoteDao
import kg.example.noteapp.data.models.NoteModel

@Database(entities = [NoteModel::class], version = 1)
@TypeConverters(Converters :: class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}