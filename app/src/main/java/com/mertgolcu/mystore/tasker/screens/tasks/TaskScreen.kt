package com.mertgolcu.mystore.tasker.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertgolcu.mystore.tasker.domain.model.Task

/**
 * @author mertgolcu
 * @since 06.10.2023
 */


fun taskCreator(): List<Task> {
    var i = 0
    var title = "Test"
    val list = arrayListOf<Task>()
    for (i in 0..15) {
        list.add(Task.create(title = "$title $i", id = i))
    }
    return list
}

val globalTaskList = taskCreator()

val viewModel = TasksViewModel()

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen() {
    val state by viewModel.state

    when (state) {
        is TaskState.Error -> {
            Text(text = "Error")
        }

        TaskState.Loading -> {
            viewModel.processIntent(TasksIntent.LoadTasks)
        }

        is TaskState.Success -> {
            //  val taskListState = viewModel.state.value?.tasks ?: listOf()
            val scope = rememberCoroutineScope()
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = MaterialTheme.colorScheme.background,
                    )
            ) {
                TaskDetailScaffold(
                    modifier = Modifier,
                    scope = scope,
                    onSave = { task ->
                        // save task
                        viewModel.processIntent(TasksIntent.CreateTask(task))
                    },
                    onDelete = { task ->
                        // delete task
                        task?.let {
                            viewModel.processIntent(TasksIntent.DeleteTask(task))
                        }
                    },
                ) { _, show, _ ->
                    TaskList(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.background,
                            ),
                        tasks = (state as TaskState.Success).tasks,
                        onTaskClick = { task ->
                            // open detail
                            show(task)
                        },
                        onTaskCheckedChange = { task, isChecked ->
                            // update task
                            viewModel.processIntent(TasksIntent.ToggleTask(task, isChecked))
                        },
                        onClickAdd = {
                            // open detail
                            show(null)
                        }
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TaskScreenPreview() {
    TaskScreen()
}
