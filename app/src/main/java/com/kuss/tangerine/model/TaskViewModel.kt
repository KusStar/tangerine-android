package com.kuss.tangerine.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kuss.tangerine.db.TaskDataBase
import com.kuss.tangerine.db.Task
import com.kuss.tangerine.db.TaskDao
import com.kuss.tangerine.db.TaskRepository
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class TaskViewModel(application: Application) : AndroidViewModel(application) {
    // The ViewModel maintains a reference to the repository to get data.
    // LiveData gives us updated words when they change.
    val unDoneTasks: LiveData<List<Task>>
    val doneTasks: LiveData<List<Task>>

    private var repository: TaskRepository? = null

    init {
        val taskDao: TaskDao = TaskDataBase.getDatabase(application, viewModelScope).taskDao()
        repository  = TaskRepository(taskDao)
        unDoneTasks = repository!!.unDoneTasks
        doneTasks = repository!!.doneTasks
    }
    /**
     * The implementation of insert() in the db is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(task: Task) = viewModelScope.launch {
        repository!!.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository!!.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository!!.delete(task)
    }

    fun deleteAll() = viewModelScope.launch {
        repository!!.deleteAll()
    }
}