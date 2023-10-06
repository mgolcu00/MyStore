package com.mertgolcu.mystore.tasker.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.mertgolcu.mystore.tasker.domain.model.Task

/**
 * @author mertgolcu
 * @since 06.10.2023
 */


fun taskCreator(): List<Task> {
    var i = 0
    var title = "Test"
    val list = arrayListOf<Task>()
    for (i in 0..25) {
        list.add(Task.create(title = "$title $i", id = i))
    }
    return list
}

val globalTaskList = taskCreator()

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen() {
    val taskListState = remember {
        mutableStateOf(globalTaskList)
    }
    val scope = rememberCoroutineScope()
    val detailTaskState = remember {
        mutableStateOf<Task?>(null)
    }
    Column {
        TaskDetailScaffold(
            task = detailTaskState.value,
            scope = scope,
            onSave = { task ->
                // save task

                taskListState.value = taskListState.value.map { t ->
                    if (t.id == task.id) {
                        task
                    } else {
                        t
                    }
                }
            },
            onDelete = { task ->
                // delete task

                task?.let {
                    taskListState.value = taskListState.value.filter { t ->
                        t.id != it.id
                    }
                }
            },
        ) { _, show, _ ->
            TaskList(
                tasks = taskListState.value,
                onTaskClick = { task ->
                    // open detail
                    show(task)
                },
                onTaskCheckedChange = { task, isChecked ->
                    // update task
                    taskListState.value = taskListState.value.map { t ->
                        if (t.id == task.id) {
                            task.updateCompleted(isChecked)
                        } else {
                            t
                        }
                    }
                },
                onClickAdd = {
                    // open detail
                    show(null)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TaskScreenPreview() {
    TaskScreen()
}
