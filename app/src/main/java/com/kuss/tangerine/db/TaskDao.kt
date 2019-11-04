package com.kuss.tangerine.db
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * from tasks ORDER BY date DESC")
    fun loadAll(): LiveData<List<Task>>

    @Query("SELECT * from tasks WHERE checked = 0 ORDER BY date DESC ")
    fun loadUnfinishedTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}
