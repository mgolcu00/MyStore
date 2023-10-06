package com.mertgolcu.mystore.tasker.screens.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertgolcu.mystore.tasker.domain.model.Task
import kotlinx.coroutines.launch

/**
 * @author mertgolcu
 * @since 04.10.2023
 */


val newTasks = listOf<Task>(
    Task.create(title = "Test1"),
    Task.create(title = "Test2"),
    Task.create(title = "Test3"),
    Task.create(title = "Test4"),
    Task.create(title = "Test5"),
    Task.create(title = "Test6"),
    Task.create(title = "Test7"),
    Task.create(title = "Test8"),
    Task.create(title = "Test9"),
    Task.create(title = "Test10"),
    Task.create(title = "Test11"),
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NewTasks() {
    //  val scope = rememberCoroutineScope()
    val taskListState = remember {
        mutableStateOf(newTasks)
    }
    var selectedTask = remember {
        mutableStateOf(Task.create(title = ""))
    }
    AddAndEditTaskModal(
        onDone = {


            // add
            taskListState.value = taskListState.value + it
        },
        onDismiss = {
            selectedTask.value = Task.create(title = "")
        },
        onDelete = {
            taskListState.value = taskListState.value.filter { task ->
                task.id != it.id
            }
            selectedTask.value = Task.create(title = "")
        }
    ) { bottomSheetState,
        keyboardController,
        focusRequester,
        scope,
        onTaskChange ->
        NewTaskList(
            taskList = taskListState.value,
            onClickAdd = {
                scope.launch {
                    onTaskChange(Task.create(title = ""))
                    bottomSheetState.bottomSheetState.expand()
                    focusRequester.requestFocus()
                     keyboardController?.show()
                }
            },
            onClick = {
                scope.launch {
                    onTaskChange(it)
                    bottomSheetState.bottomSheetState.expand()
                    //focusRequester.requestFocus()
                    // keyboardController?.show()
                }
            },
            onCheckedChange = {
                scope.launch {
                    taskListState.value = taskListState.value.map { task ->
                        if (task.id == selectedTask.value.id) {
                            task.updateCompleted(it)
                        } else {
                            task
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun NewTaskList(
    taskList: List<Task>,
    onCheckedChange: (Boolean) -> Unit = {},
    onClick: (task: Task) -> Unit = {},
    onClickAdd: () -> Unit = {},
) {
    LazyColumn {
        items(taskList.size) { index ->
            NewTaskItem(
                task = taskList[index],
                onClick = onClick,
                onCheckedChange = onCheckedChange
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            if (index == taskList.size - 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = CenterVertically
                ) {
                    TextButton(
                        modifier = Modifier.align(CenterVertically),
                        onClick = {
                            onClickAdd()
                        },
                    ) {
                        Text(
                            modifier = Modifier.align(CenterVertically),
                            text = "Add New Task",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun NewTaskItem(
    task: Task,
    onClick: (task: Task) -> Unit = {},
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ClickableText(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(CenterVertically),
            text = AnnotatedString(task.title),
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            onClick = {
                onClick(task)
            }
        )
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = {
                onCheckedChange(it)
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun NewTaskItemPreview() {
    NewTasks()
}