package com.gamil.moahear.noteappmvp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gamil.moahear.noteappmvp.utils.Constants

@Entity(tableName = Constants.NOTES_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String = "",
    var description: String = "",
    var category: String = "",
    var priority: String = ""
)
