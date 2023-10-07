package com.mertgolcu.mystore.tasker.screens.tasks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mertgolcu.mystore.tasker.domain.model.Task

/**
 * @author mertgolcu
 * @since 27.09.2023
 */
class TasksViewModel : ViewModel() {

    private val _state = MutableLiveData(TasksState())
    val state = _state

    fun processIntent(intent:TasksIntent){
        when(intent){
            TasksIntent.LoadTasks -> {

            }
            is TasksIntent.CreateTask -> {
                addTask(intent.task)
            }
            is TasksIntent.DeleteTask -> {
                deleteTask(intent.task)
            }
            is TasksIntent.ToggleTask -> {
                toggleTask(intent.task,intent.isCompleted)
            }
            is TasksIntent.UpdateTask -> {
                updateTask(intent.task)
            }
        }
    }

    private fun addTask(task: Task) {
        val currentTasks = _state.value?.tasks ?: emptyList()
        _state.value = _state.value?.copy(tasks = currentTasks + task)
    }

    private fun deleteTask(task: Task) {
        val currentTasks = _state.value?.tasks ?: emptyList()
        _state.value = _state.value?.copy(tasks = currentTasks - task)
    }

    private fun updateTask(task: Task) {
        val currentTasks = _state.value?.tasks ?: emptyList()
        _state.value = _state.value?.copy(tasks = currentTasks.map { t ->
            if (t.id == task.id) {
                task
            } else {
                t
            }
        })
    }

    private fun toggleTask(task: Task,isCompleted: Boolean) {
        val currentTasks = _state.value?.tasks ?: emptyList()
        _state.value = _state.value?.copy(tasks = currentTasks.map { t ->
            if (t.id == task.id) {
                task.updateCompleted(isCompleted)
            } else {
                t
            }
        })
    }

    private fun loadTasks() {
        _state.value = _state.value?.copy(tasks = taskCreator())
    }

}

data class TasksState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)

sealed class TasksIntent {
    data object LoadTasks : TasksIntent()
    data class ToggleTask(val task: Task,val isCompleted:Boolean) : TasksIntent()
    data class DeleteTask(val task: Task) : TasksIntent()
    data class UpdateTask(val task: Task) : TasksIntent()
    data class CreateTask(val task: Task) : TasksIntent()
}