package com.bacchoterra.memoriav3

import android.app.Application
import com.bacchoterra.memoriav3.database.NoteDatabase
import com.bacchoterra.memoriav3.repo.CategoryRepo
import com.bacchoterra.memoriav3.repo.NoteRepository

class MemoriaApplication : Application() {

    private val database by lazy { NoteDatabase.getInstance(this) }
    val catRepository by lazy { CategoryRepo(database.getCatDao()) }
    val noteRepository by lazy { NoteRepository(database.getNoteDao()) }

}