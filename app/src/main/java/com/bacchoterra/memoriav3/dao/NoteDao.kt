package com.bacchoterra.memoriav3.dao

import androidx.room.*
import com.bacchoterra.memoriav3.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note:Note)

    @Delete
    suspend fun delete(note:Note)

    @Update
    suspend fun update(note:Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table WHERE category = :cat")
    fun getAllFromCategory(cat:String):Flow<List<Note>>

}