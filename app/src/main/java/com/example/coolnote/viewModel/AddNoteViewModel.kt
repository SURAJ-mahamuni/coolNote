package com.example.coolnote.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolnote.model.NoteModel
import com.example.coolnote.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModel() {

    fun addNote(noteModel: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.addNote(noteModel)
        }
    }
}