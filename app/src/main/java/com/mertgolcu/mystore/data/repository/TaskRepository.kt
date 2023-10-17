package com.mertgolcu.mystore.data.repository

import com.mertgolcu.mystore.data.local.daos.TaskDao
import com.mertgolcu.mystore.data.local.entities.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author mertgolcu
 * @since 16.10.2023
 */

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    fun getTaskById(taskId: Int): Flow<Task> {
        return taskDao.getTaskById(taskId)
    }

    fun searchTasks(searchQuery: String): Flow<List<Task>> {
        return taskDao.searchTasks(searchQuery)
    }

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }
}