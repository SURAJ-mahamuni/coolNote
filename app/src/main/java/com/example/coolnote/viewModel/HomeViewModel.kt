package com.example.coolnote.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.coolnote.model.NoteModel
import com.example.coolnote.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModel() {

    fun getAllNote(): LiveData<List<NoteModel>> {
        return databaseRepository.getAllNote().asLiveData(Dispatchers.Main)
    }
    fun getNotes(searchItem : String) : LiveData<List<NoteModel>>{
        return databaseRepository.getNotes(searchItem).asLiveData(Dispatchers.Main)
    }
}