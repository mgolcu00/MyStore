package com.mertgolcu.mystore.tasker.screens.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertgolcu.mystore.tasker.domain.model.Task

/**
 * @author mertgolcu
 * @since 27.09.2023
 */

@Composable
fun TasksScreen() {

}

val tasks =
    listOf<Task>(
        Task.create(title = "Test"),
        Task.create(title = "Test2"),
        Task.create(title = "Test3"),
        Task.create(title = "Test4"),
        Task.create(title = "Test5"),
        Task.create(title = "Test6"),
        Task.create(title = "Test7"),
    )

@Composable
fun TaskList(tasks: List<Task>) {
    val tasksState = remember { mutableStateOf(tasks) }
    LazyColumn {
        items(tasksState.value.size) { index ->
            TaskItem(tasksState.value[index])
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    var isChecked = remember { mutableStateOf(task.isCompleted) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Column {
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = task.title,
                maxLines = 1,
                minLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = task.getFormattedCreatedAt() ?: "",
            )
        }
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically)
        ) {
            Checkbox(checked = isChecked.value, onCheckedChange = {
                isChecked.value = it
            })
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun TaskPreview() {
    val task = Task.create(title = "Test")
    TaskList(tasks)
}
