package com.bacchoterra.memoriav3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    val noteTitle: String,
    val noteBody: String,
    val category: String,
    val timeStamp: Long,
    val importance:Int = 0,
    val photoUri:String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {


}