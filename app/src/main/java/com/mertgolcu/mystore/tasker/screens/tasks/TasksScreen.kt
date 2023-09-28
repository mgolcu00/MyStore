package com.mertgolcu.mystore.tasker.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mertgolcu.mystore.tasker.domain.model.Task
import kotlinx.coroutines.launch

/**
 * @author mertgolcu
 * @since 27.09.2023
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen() {
    var showAddTaskDialog = remember { mutableStateOf(false) }
    var taskListState = remember { mutableStateOf(tasks) }
    var showFloatingActionButton = remember { mutableStateOf(true) }
    var sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    Scaffold(
        floatingActionButton = {
            if (showFloatingActionButton.value && !sheetState.isVisible) {
                FloatingActionButton(modifier = Modifier, onClick = {
                    showAddTaskDialog.value = true
                }) {
                    Text(
                        modifier = Modifier.padding(8.dp), text = "+ Add Task"
                    )
                }
            }
        },
    ) { pv ->
        if (showAddTaskDialog.value) {
            ModalBottomSheet(
                modifier = Modifier.offset(
                    y = if (sheetState.isVisible) 0.dp else 200.dp
                ),
                onDismissRequest = {
                    showAddTaskDialog.value = false
                },
                sheetState = sheetState,
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface
                        )
                        .fillMaxWidth(),
                ) {
                    ExpandedTaskRow(
                        task = Task.create(title = ""),
                        onDone = {
                            taskListState.value = taskListState.value.toMutableList().apply {
                                add(it)
                            }

                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showAddTaskDialog.value = false
                                }
                            }
                        },
                        onCancel = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showAddTaskDialog.value = false
                                }
                            }
                        }
                    )
                }
            }
        } else {
            TaskList(
                modifier = Modifier.padding(pv),
                tasks = taskListState.value,
                expandListener = {
                    showFloatingActionButton.value = !it
                }
            )
        }
    }
}

val tasks = listOf<Task>(
    Task.create(title = "Test"),
    Task.create(title = "Test2"),
    Task.create(title = "Test3"),
    Task.create(title = "Test4"),
    Task.create(title = "Test5"),
    Task.create(title = "Test6"),
    Task.create(title = "Test7"),
    Task.create(title = "Test7"),
    Task.create(title = "Test7"),
    Task.create(title = "Test7"),
    Task.create(title = "Test7"),
)

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    expandListener: (Boolean) -> Unit = {}
) {
    val tasksState = remember { mutableStateOf(tasks) }
    var expandedIndexState = remember { mutableIntStateOf(-1) }
    LazyColumn(
        modifier = modifier
    ) {
        items(tasksState.value.size) { index ->
            SmartTaskRow(
                tasksState.value[index],
                onDone = {
                    tasksState.value = tasksState.value.toMutableList().apply {
                        set(index, it)
                    }
                },
                expanded = expandedIndexState.intValue == index,
                onExpand = {
                    if (!it) {
                        expandedIndexState.value = -1
                        expandListener(false)
                    } else {
                        expandedIndexState.value = index
                        expandListener(true)
                    }
                },
            )
            if (index != tasksState.value.size - 1) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                )
            } else {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, isExpanded: Boolean = false, onClick: () -> Unit = {}) {
    var isExpandedState = remember { mutableStateOf(isExpanded) }
    var isChecked = remember { mutableStateOf(task.isCompleted) }
    var taskTitleState = remember { mutableStateOf(TextFieldValue(task.title)) }
    val focusRequester = remember { FocusRequester() }
    val errorState = remember { mutableStateOf("") }
    val maxCharCount = 100
    val maxLineBreakCount = 3
    if (isExpandedState.value) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .focusRequester(focusRequester)
                    .align(Alignment.CenterVertically),
                value = taskTitleState.value,
                maxLines = 3,
                label = { Text("Title") },
                onValueChange = {
                    if (it.text.isEmpty()) {
                        errorState.value = "Title can not be empty"
                    } else {
                        errorState.value = ""
                    }
                    if (it.text.length > maxCharCount) {
                        taskTitleState.value = taskTitleState.value.copy(
                            text = it.text.substring(0, maxCharCount),
                            selection = TextRange(maxCharCount)
                        )
                        return@TextField
                    }
                    if (it.text.count { char -> char == '\n' } > maxLineBreakCount) {
                        taskTitleState.value = taskTitleState.value.copy(
                            text = it.text.substring(0, it.text.lastIndexOf('\n')),
                            selection = TextRange(it.text.lastIndexOf('\n'))
                        )
                        return@TextField
                    }
                    val text = it
                    taskTitleState.value = text
                    task.updateTitle(text.text)
                },
                textStyle = MaterialTheme.typography.headlineSmall,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        if (taskTitleState.value.text.isEmpty()) {
//                            errorState.value = "Title can not be empty"
//                        } else {
//                            isExpandedState.value = false
//                        }
//                    },
//                ),
                isError = errorState.value.isNotEmpty(),
                supportingText = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (errorState.value.isNotEmpty()) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = errorState.value
                            )
                        } else {
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = "${taskTitleState.value.text.length}/$maxCharCount"
                            )
                        }
                    }
                },
            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
                taskTitleState.value = taskTitleState.value.copy(
                    selection = TextRange(taskTitleState.value.text.length)
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically)
            ) {
                TextButton(modifier = Modifier.wrapContentSize(), onClick = {
                    if (taskTitleState.value.text.isEmpty()) {
                        errorState.value = "Title can not be empty"
                    } else {
                        isExpandedState.value = false
                    }
                }) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically), text = "Done"
                    )
                }
                TextButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = {
                        isExpandedState.value = false
                    },
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.error,
                        text = "Cancel"
                    )
                }
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
    ) {
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
                    text = taskTitleState.value.text, paragraphStyle = ParagraphStyle()
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
fun CollapsedTaskTow(
    task: Task, onClick: () -> Unit = {}, onCheckedChange: (Task, Boolean) -> Unit = { _, _ -> }
) {
    var titleState = remember { mutableStateOf(task.title) }
    var isChecked = remember { mutableStateOf(task.isCompleted) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            ClickableText(
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxWidth(0.8f),
                text = AnnotatedString(
                    text = titleState.value, paragraphStyle = ParagraphStyle()
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                onClick = {
                    onClick()
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
                onCheckedChange(task, it)
            })
        }
    }
}

@Composable
fun ExpandedTaskRow(task: Task, onDone: (task: Task) -> Unit = {}, onCancel: () -> Unit = {}) {
    var titleState = remember { mutableStateOf(TextFieldValue(task.title)) }
    var focusRequester = remember { FocusRequester() }
    var errorState = remember { mutableStateOf("") }
    val maxCharCount = 100
    val maxLineBreakCount = 3

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .focusRequester(focusRequester)
                .align(Alignment.CenterVertically),
            value = titleState.value,
            maxLines = 3,
            label = { Text("Title") },
            onValueChange = {
                if (it.text.isEmpty()) {
                    errorState.value = "Title can not be empty"
                } else {
                    errorState.value = ""
                }
                if (it.text.length > maxCharCount) {
                    titleState.value = titleState.value.copy(
                        text = it.text.substring(0, maxCharCount),
                        selection = TextRange(maxCharCount)
                    )
                    return@TextField
                }
                if (it.text.count { char -> char == '\n' } > maxLineBreakCount) {
                    titleState.value = titleState.value.copy(
                        text = it.text.substring(0, it.text.lastIndexOf('\n')),
                        selection = TextRange(it.text.lastIndexOf('\n'))
                    )
                    return@TextField
                }
                val text = it
                titleState.value = text
                task.updateTitle(text.text)
            },
            textStyle = MaterialTheme.typography.headlineSmall,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        if (taskTitleState.value.text.isEmpty()) {
//                            errorState.value = "Title can not be empty"
//                        } else {
//                            isExpandedState.value = false
//                        }
//                    },
//                ),
            isError = errorState.value.isNotEmpty(),
            supportingText = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (errorState.value.isNotEmpty()) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = errorState.value
                        )
                    } else {
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "${titleState.value.text.length}/$maxCharCount"
                        )
                    }
                }
            },
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            titleState.value = titleState.value.copy(
                selection = TextRange(titleState.value.text.length)
            )
        }
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically)
        ) {
            TextButton(modifier = Modifier.wrapContentSize(), onClick = {
                if (titleState.value.text.isEmpty()) {
                    errorState.value = "Title can not be empty"
                } else {
                    onDone(task.copy(title = titleState.value.text))
                }
            }) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically), text = "Save"
                )
            }
            TextButton(
                modifier = Modifier.wrapContentSize(),
                onClick = {
                    onCancel()
                },
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = MaterialTheme.colorScheme.error,
                    text = "Cancel"
                )
            }
        }
    }
}


@Composable
fun SmartTaskRow(
    task: Task,
    onDone: (task: Task) -> Unit = {},
    expanded: Boolean = false,
    onExpand: (Boolean) -> Unit = {},
) {
    if (expanded) {
        ExpandedTaskRow(task = task, onDone = {
            onDone(it)
            onExpand(false)
        }, onCancel = {
            onExpand(false)
        })
    } else {
        CollapsedTaskTow(task = task, onClick = {
            onExpand(true)
        }, onCheckedChange = { task, isChecked ->
            onDone(task.updateCompleted(isChecked))
        })
    }

}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun TaskPreview() {
    TasksScreen()
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun TaskItemPreview() {
    val task = Task.create(title = "Test")
    Column {
        SmartTaskRow(task = task)
    }
}
