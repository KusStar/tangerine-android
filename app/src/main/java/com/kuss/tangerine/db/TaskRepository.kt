package com.kuss.tangerine.db

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole db, because you only need access to the DAO
class TaskRepository(private val taskDao: TaskDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTasks: LiveData<List<Task>> = taskDao.loadUnfinishedTasks()

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun deleteAll() {
        taskDao.deleteAll()
    }

}