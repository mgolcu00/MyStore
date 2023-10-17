package com.mertgolcu.mystore.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mertgolcu.mystore.data.local.entities.Task
import kotlinx.coroutines.flow.Flow

/**
 * @author mertgolcu
 * @since 16.10.2023
 */
@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    // queries

    // get all tasks from db ordered by created date
    @Query("SELECT * FROM tasks ORDER BY updatedAt DESC")
     fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id=:taskId")
     fun getTaskById(taskId: Int): Flow<Task>

    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :searchQuery || '%' ORDER BY updatedAt DESC")
     fun searchTasks(searchQuery: String): Flow<List<Task>>


}