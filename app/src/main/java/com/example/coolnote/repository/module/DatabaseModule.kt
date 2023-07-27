package com.example.coolnote.repository.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coolnote.dao.NoteDao
import com.example.coolnote.db.NoteDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): NoteDataBase {
        return Room.databaseBuilder(context, NoteDataBase::class.java, "CoolNoteDatabase").build()
    }

    @Provides
    @Singleton
    fun getNoteDao(noteDataBase: NoteDataBase): NoteDao {
        return noteDataBase.getNoteDao()
    }
}