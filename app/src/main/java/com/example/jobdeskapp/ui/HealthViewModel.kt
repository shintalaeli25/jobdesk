package com.example.jobdeskapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.jobdeskapp.model.Jobdesk
import com.example.jobdeskapp.repository.JobdeskRepository
import kotlinx.coroutines.launch

class JobdeskViewModel (private val repository: JobdeskRepository): ViewModel() {
    val allJobdesk: LiveData<List<Jobdesk>> = repository.allJobdesk.asLiveData()

    fun insert(jobdesk: Jobdesk) = viewModelScope.launch {
        repository.insertJobdesk(jobdesk)
    }

    fun delete(jobdesk: Jobdesk) = viewModelScope.launch {
        repository.deleteJobdesk(jobdesk)
    }

    fun update(jobdesk: Jobdesk) = viewModelScope.launch {
        repository.updateJobdesk(jobdesk)
    }
}

class JobdeskViewModelFactory(private val repository: JobdeskRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom((JobdeskViewModel::class.java))){
            return JobdeskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}