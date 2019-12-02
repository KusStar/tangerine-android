package com.kuss.tangerine.db

import androidx.lifecycle.LiveData


class TaskRepository(private val taskDao: TaskDao) {

    val unDoneTasks: LiveData<List<Task>> = taskDao.loadUnDoneTasks()
    val doneTasks: LiveData<List<Task>> = taskDao.loadDoneTasks()

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

    suspend fun deleteDoneTasks() {
        taskDao.deleteDoneTasks()
    }

}