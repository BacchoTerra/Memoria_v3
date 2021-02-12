package com.bacchoterra.memoriav3.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bacchoterra.memoriav3.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(category:Category)

    @Delete
    suspend fun delete(category:Category)

    @Query("DELETE FROM categoria_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM categoria_table")
    fun getAll():Flow<List<Category>>

}