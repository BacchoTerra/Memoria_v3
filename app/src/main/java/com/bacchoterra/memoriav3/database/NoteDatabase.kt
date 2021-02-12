package com.bacchoterra.memoriav3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bacchoterra.memoriav3.dao.CategoryDao
import com.bacchoterra.memoriav3.dao.NoteDao
import com.bacchoterra.memoriav3.model.Category
import com.bacchoterra.memoriav3.model.Note

@Database(entities = [Note::class, Category::class], version = 1, exportSchema = false)
abstract class NoteDatabase() : RoomDatabase() {

    abstract fun getCatDao(): CategoryDao
    abstract fun getNoteDao(): NoteDao

    companion object {

        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance: NoteDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "notes_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}