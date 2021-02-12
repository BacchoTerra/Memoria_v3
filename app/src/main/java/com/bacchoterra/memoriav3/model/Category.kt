package com.bacchoterra.memoriav3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoria_table")
data class Category(
    val name: String,
    val isLocked: Boolean = false,
    val lastNote:String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {


}