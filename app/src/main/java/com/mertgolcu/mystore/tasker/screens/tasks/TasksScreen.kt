package com.mertgolcu.mystore.tasker.screens.tasks

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.ParagraphStyle
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
    var expandedTaskIndex = remember { mutableStateOf<Int?>(null) }
    LazyColumn {
        items(tasksState.value.size) { index ->
            TaskItem(tasksState.value[index], expandedTaskIndex.value == index)
        }
    }
}

@Composable
fun TaskItem(task: Task, isExpanded: Boolean = false, onClick: () -> Unit = {}) {
    var isExpandedState = remember { mutableStateOf(isExpanded) }
    var isChecked = remember { mutableStateOf(task.isCompleted) }
    var taskTitleState = remember { mutableStateOf(task.title) }
    val focusRequester = remember { FocusRequester() }
    if (isExpandedState.value) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    MaterialTheme.shapes.small
                )
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            BasicTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = taskTitleState.value,
                maxLines = 3,
                onValueChange = {
                    taskTitleState.value = it
                    task.updateTitle(it)
                },
                textStyle = MaterialTheme.typography.headlineLarge,
            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            TextButton(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(4.dp)
                    .align(Alignment.CenterVertically),
                onClick = {
                    isExpandedState.value = false
                }) {
                Text(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterVertically),
                    text = "Done"
                )
            }
        }
        return
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Column {
            /*
             style = MaterialTheme.typography.headlineMedium,
               text = task.title,
               maxLines = 1,
               minLines = 1,
               overflow = TextOverflow.Ellipsis,
             */
            ClickableText(
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxWidth(0.7f),
                text = AnnotatedString(
                    text = taskTitleState.value,
                    paragraphStyle = ParagraphStyle()
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                onClick = {
                    isExpandedState.value = true
                },
                style = MaterialTheme.typography.headlineSmall,
                softWrap = false,
            )

            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxWidth(0.7f),
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

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun TaskItemPreview() {
    val task = Task.create(title = "Test")
    TaskItem(task, true)
}
