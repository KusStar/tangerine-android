package com.kuss.tangerine.db

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole db, because you only need access to the DAO
class TaskRepository(private val taskDao: TaskDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val unDoneTasks: LiveData<List<Task>> = taskDao.loadUnDoneTasks()
    val doneTasks: LiveData<List<Task>> = taskDao.loadDoneTasks()

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

    suspend fun deleteDoneTasks() {
        taskDao.deleteDoneTasks()
    }

}