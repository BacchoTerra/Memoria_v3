package com.bacchoterra.memoriav3.viewmodel

import androidx.lifecycle.*
import com.bacchoterra.memoriav3.model.Note
import com.bacchoterra.memoriav3.repo.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repo: NoteRepository):ViewModel() {

    fun getAllNotesFromCat(cat:String):LiveData<List<Note>>{

        return repo.getAllNotesFromCat(cat).asLiveData()

    }

    fun insert(note: Note) = viewModelScope.launch {
        repo.insert(note)
    }

    fun delete(note:Note) = viewModelScope.launch {
        repo.delete(note)
    }

    fun deleteAll() = viewModelScope.launch {
        repo.deleteAll()
    }


}

class NoteViewModelFactory(repo:NoteRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            return modelClass as T
        }

        throw IllegalArgumentException("Unknown viewModel class")

    }

}