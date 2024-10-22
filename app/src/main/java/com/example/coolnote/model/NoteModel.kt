package com.example.coolnote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteTable")
data class NoteModel(
    @PrimaryKey(autoGenerate = true) var noteId: Int? = null,
    var noteTitle: String? = null,
    var noteInfo: String? = null,
)
