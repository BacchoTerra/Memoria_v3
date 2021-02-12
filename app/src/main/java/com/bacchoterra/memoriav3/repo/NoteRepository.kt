package com.bacchoterra.memoriav3.repo

import com.bacchoterra.memoriav3.dao.NoteDao
import com.bacchoterra.memoriav3.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val mDao:NoteDao) {


    suspend fun insert (note:Note){
        mDao.insert(note)
    }

    suspend fun delete(note:Note){
        mDao.delete(note)
    }

    suspend fun deleteAll() = mDao.deleteAll()

    fun getAllNotesFromCat(cat:String):Flow<List<Note>>{
        return mDao.getAllFromCategory(cat)
    }



}