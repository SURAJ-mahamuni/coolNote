package com.example.coolnote.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coolnote.dao.NoteDao
import com.example.coolnote.model.NoteModel

@Database(entities = [NoteModel::class], version = 1, exportSchema = false)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}