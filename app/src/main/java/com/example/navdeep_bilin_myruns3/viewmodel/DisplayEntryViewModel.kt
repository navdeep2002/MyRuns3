package com.example.navdeep_bilin_myruns3.viewmodel

import androidx.lifecycle.*
import com.example.navdeep_bilin_myruns3.data.ExerciseEntryEntity
import com.example.navdeep_bilin_myruns3.data.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DisplayEntryViewModel(
    private val repo: ExerciseRepository,
    private val entryId: Long
) : ViewModel() {

    private val _entry = MutableLiveData<ExerciseEntryEntity?>()
    val entry: LiveData<ExerciseEntryEntity?> = _entry

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            _entry.postValue(repo.getById(entryId))
        }
    }

    fun delete(onDone: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteById(entryId)
            onDone()
        }
    }
}

class DisplayEntryVMFactory(
    private val repo: ExerciseRepository,
    private val id: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DisplayEntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DisplayEntryViewModel(repo, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
