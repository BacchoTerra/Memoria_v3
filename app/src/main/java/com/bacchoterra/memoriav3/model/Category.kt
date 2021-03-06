package com.bacchoterra.memoriav3.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "categoria_table")
data class Category(
    val name: String,
    val isLocked: Boolean = false,
    val lastNoteBody:String = "",
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) :Serializable{


}