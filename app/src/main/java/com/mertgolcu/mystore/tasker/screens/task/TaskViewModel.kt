package com.mertgolcu.mystore.tasker.screens.task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mertgolcu.mystore.tasker.domain.model.Task

/**
 * @author mertgolcu
 * @since 27.09.2023
 */
class TaskViewModel : ViewModel() {

    private var newTaskList = taskCreator()
    private val _state = mutableStateOf<TaskState>(TaskState.Loading)
    val state: State<TaskState> = _state

    init {
        processIntent(TasksIntent.LoadTasks)
    }
    fun processIntent(intent: TasksIntent) {
        when (intent) {
            TasksIntent.LoadTasks -> {
                loadTasks()
            }

            is TasksIntent.CreateTask -> {
                addTask(intent.task)
            }

            is TasksIntent.DeleteTask -> {
                deleteTask(intent.task)
            }

            is TasksIntent.ToggleTask -> {
                toggleTask(intent.task, intent.isCompleted)
            }

            is TasksIntent.UpdateTask -> {
                updateTask(intent.task)
            }
        }
    }

    private fun addTask(task: Task) {
        // is task have run update
        if (newTaskList.count { it.id == task.id } > 0) {
            updateTask(task)
            return
        }
        newTaskList = newTaskList + task
        _state.value = TaskState.Success(newTaskList)
    }

    private fun deleteTask(task: Task) {
        newTaskList = newTaskList.filter { it.id != task.id }
        _state.value = TaskState.Success(newTaskList)
    }

    private fun updateTask(task: Task) {
        newTaskList = newTaskList.map { t ->
            if (t.id == task.id) {
                task
            } else {
                t
            }
        }
        _state.value = TaskState.Success(newTaskList)
    }

    private fun toggleTask(task: Task, isCompleted: Boolean) {
        val newTask = task.copy(isCompleted = isCompleted)
        updateTask(newTask)
    }

    private fun loadTasks() {
        _state.value = TaskState.Success(newTaskList)
    }


}

sealed class TaskState {
    data object Loading : TaskState()
    data class Error(val error: Throwable) : TaskState()
    data class Success(val tasks: List<Task>) : TaskState()
}

sealed class TasksIntent {
    data object LoadTasks : TasksIntent()
    data class ToggleTask(val task: Task, val isCompleted: Boolean) : TasksIntent()
    data class DeleteTask(val task: Task) : TasksIntent()
    data class UpdateTask(val task: Task) : TasksIntent()
    data class CreateTask(val task: Task) : TasksIntent()
}