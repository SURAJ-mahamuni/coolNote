package com.example.coolnote.repository

import com.example.coolnote.dao.NoteDao
import com.example.coolnote.model.NoteModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val noteDao: NoteDao) {

    fun getAllNote(): kotlinx.coroutines.flow.Flow<List<NoteModel>> {
        return noteDao.getAllNote()
    }

    fun addNote(noteModel: NoteModel) {
        noteDao.addNote(noteModel)
    }
    fun getNotes(searchItem: String): Flow<List<NoteModel>> {
        return noteDao.getNotes(searchItem)
    }
}