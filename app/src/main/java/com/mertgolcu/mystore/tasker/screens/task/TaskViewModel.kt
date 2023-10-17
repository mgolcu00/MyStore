package com.mertgolcu.mystore.tasker.screens.task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mertgolcu.mystore.data.local.entities.Task
import com.mertgolcu.mystore.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author mertgolcu
 * @since 27.09.2023
 */
@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

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
        viewModelScope.launch {
            taskRepository.insert(task)
        }
    }

    private fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.delete(task)
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.update(task)
        }
    }

    private fun toggleTask(task: Task, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.update(task.copy(isCompleted = isCompleted))
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            taskRepository.getAllTasks().catch {
                _state.value = TaskState.Error(it)
            }.collect {
                _state.value = TaskState.Success(it)
            }
        }

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