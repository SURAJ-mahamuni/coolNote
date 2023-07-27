package com.example.coolnote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coolnote.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("select * from noteTable")
    fun getAllNote(): Flow<List<NoteModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(noteModel: NoteModel)

    @Query("select * from noteTable where noteTitle like :searchItem ")
    fun getNotes(searchItem: String): Flow<List<NoteModel>>
}