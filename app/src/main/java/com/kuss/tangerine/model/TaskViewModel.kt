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


class TaskViewModel(application: Application) : AndroidViewModel(application) {

    val unDoneTasks: LiveData<List<Task>>
    val doneTasks: LiveData<List<Task>>

    private var repository: TaskRepository? = null

    init {
        val taskDao: TaskDao = TaskDataBase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        unDoneTasks = repository!!.unDoneTasks
        doneTasks = repository!!.doneTasks
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository!!.insert(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository!!.delete(task)
    }

    fun deleteDoneTasks() = viewModelScope.launch {
        repository!!.deleteDoneTasks()
    }
}