package com.bacchoterra.memoriav3.viewmodel

import androidx.lifecycle.*
import com.bacchoterra.memoriav3.model.Category
import com.bacchoterra.memoriav3.model.Note
import com.bacchoterra.memoriav3.repo.CategoryRepo
import kotlinx.coroutines.launch
import kotlin.IllegalArgumentException

class CategoryViewModel(val repo:CategoryRepo):ViewModel() {

    fun getAllCategories():LiveData<List<Category>>{

        return repo.getAllCats.asLiveData()
    }

    fun insert(cat:Category) = viewModelScope.launch {
        repo.insert(cat)
    }

    fun delete(cat:Category) = viewModelScope.launch {
        repo.delete(cat)
    }

    fun deleteAll() = viewModelScope.launch {
        repo.deleteAll()
    }


}

class CategoryViewModelFactory(val repo:CategoryRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)){
            return modelClass as T
        }

        throw IllegalArgumentException("Unknown viewModel class")

    }


}