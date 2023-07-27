package com.example.coolnote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteTable")
data class NoteModel(
    @PrimaryKey(autoGenerate = true) val noteId: Int?,
    val noteTitle: String,
    val noteInfo: String
)
