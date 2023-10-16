package com.mertgolcu.mystore.tasker.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertgolcu.mystore.tasker.common.loading.shimmer
import com.mertgolcu.mystore.tasker.domain.model.Task
import com.mertgolcu.mystore.tasker.screens.destinations.TaskDetailScreenDestination
import com.mertgolcu.mystore.tasker.screens.task.detail.TaskDetailTransitions
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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

val viewModel = TaskViewModel()

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(modifier: Modifier = Modifier) {
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
                modifier = modifier
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

@RootNavGraph(start = true)
@Destination(
    route = "tasks",
    style = TaskDetailTransitions::class,
)
@Composable
fun TaskScreenWithoutBottomSheet(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator? = null,
) {
    val state by viewModel.state
    when (state) {
        is TaskState.Error -> {
            Text(text = "Error")
        }

        TaskState.Loading -> {
            Column(
                modifier = modifier.clip(RoundedCornerShape(8.dp))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                        )
                ) {
                    items(20) {
                        if (it % 2 == 0) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .shimmer(
                                        shape = RoundedCornerShape(8.dp)
                                    ), text = ""
                            )

                        } else {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .padding(8.dp)
                                    .shimmer(
                                        shape = RoundedCornerShape(8.dp)
                                    ), text = ""
                            )

                        }
                    }
                }
            }
            LaunchedEffect(Unit) {
                viewModel.processIntent(TasksIntent.LoadTasks)
            }
        }

        is TaskState.Success -> {
            TaskList(state = state, modifier = modifier, navigator = navigator)
        }
    }

}

@Composable
fun TaskList(
    state: TaskState,
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator? = null,
) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(8.dp))
    ) {
        TaskList(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                ),
            tasks = globalTaskList,
            onTaskClick = { task ->
                // open detail
            },
            onTaskCheckedChange = { task, isChecked ->
                // update task
            },
            onClickAdd = {
                // open detail
                navigator?.navigate(TaskDetailScreenDestination)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TaskScreenPreview() {
    TaskScreenWithoutBottomSheet()
}
