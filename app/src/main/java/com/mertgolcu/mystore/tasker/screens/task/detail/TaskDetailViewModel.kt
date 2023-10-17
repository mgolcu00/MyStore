package com.mertgolcu.mystore.tasker.screens.task.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mertgolcu.mystore.data.local.entities.Task
import com.mertgolcu.mystore.data.repository.TaskRepository
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author mertgolcu
 * @since 16.10.2023
 */
@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _state = mutableStateOf<TaskDetailState>(TaskDetailState.Loading)
    val state: State<TaskDetailState> = _state


    fun processIntent(intent: TaskDetailIntent) {
        when (intent) {
            is TaskDetailIntent.LoadTask -> {
                loadTask(intent.id)
            }

            is TaskDetailIntent.CreateTask -> {
                createTask(intent.text)
            }

            is TaskDetailIntent.UpdateTask -> {
                updateTask(intent.task)
            }

            is TaskDetailIntent.DeleteTask -> {
                deleteTask(intent.task)
            }

        }
    }


    private fun createTask(text: String) {
        viewModelScope.launch {
            if (text.isNotEmpty())
                taskRepository.insert(Task(title = text))
            // navigate to task list
        }
    }

    private fun loadTask(id: Int? = null) {
        viewModelScope.launch {
            if (id == null) {
                _state.value = TaskDetailState.Success(null)
                return@launch
            }
            taskRepository.getTaskById(id)
                .catch { e ->
                    _state.value = TaskDetailState.Error(e)
                }
                .collect { task ->
                    _state.value = TaskDetailState.Success(task)
                }
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.update(task)
        }
    }

    private fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.delete(task)
        }
    }

}

sealed class TaskDetailState {
    data object Loading : TaskDetailState()
    data class Error(val error: Throwable) : TaskDetailState()
    data class Success(val task: Task?) : TaskDetailState()

}

sealed class TaskDetailIntent {
    data class LoadTask(val id: Int? = null) : TaskDetailIntent()
    data class UpdateTask(val task: Task) : TaskDetailIntent()
    data class DeleteTask(val task: Task) : TaskDetailIntent()
    data class CreateTask(val text: String) : TaskDetailIntent()

}