package com.mertgolcu.mystore.tasker.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertgolcu.mystore.tasker.domain.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author mertgolcu
 * @since 06.10.2023
 */

@Composable
fun TaskRow(
    task: Task,
    modifier: Modifier = Modifier,
    onClick: (Task) -> Unit = {},
    onCheckedChange: (Task, Boolean) -> Unit = { _, _ -> }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ClickableText(modifier = Modifier
            .align(CenterVertically)
            .padding(4.dp)
            .weight(0.9f),
            text = AnnotatedString(
                text = task.title,
            ),
            maxLines = 1,
            style = MaterialTheme.typography.headlineSmall,
            overflow = TextOverflow.Ellipsis,
            onClick = {
                onClick.invoke(task)
            })
        Checkbox(modifier = Modifier
            .align(CenterVertically)
            .padding(4.dp)
            .weight(0.1f),
            checked = task.isCompleted,
            onCheckedChange = {
                onCheckedChange.invoke(task, it)
            })
    }
}

@Composable
@Preview(showBackground = true)
fun TaskRowPreview() {
    TaskRow(task = Task.create(title = "Send email to research team at Morning"))
}

@Composable
fun AddTaskRow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ExtendedFloatingActionButton(modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 32.dp,
                vertical = 8.dp
            ),
            onClick = {
                onClick.invoke()
            }) {
            Icon(
                modifier = Modifier.align(CenterVertically),
                imageVector = Icons.Rounded.Add,
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                modifier = Modifier.align(CenterVertically),
                text = "Add New Task",
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddTaskRowPreview() {
    AddTaskRow()
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onTaskClick: (Task) -> Unit = {},
    onTaskCheckedChange: (Task, Boolean) -> Unit = { _, _ -> },
    onClickAdd: () -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(tasks.size) { index ->
            TaskRow(task = tasks[index], onClick = {
                onTaskClick.invoke(it)
            }, onCheckedChange = { task, isChecked ->
                onTaskCheckedChange.invoke(task, isChecked)
            })
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
            )
            if (index == tasks.size - 1) {
                AddTaskRow {
                    onClickAdd()
                }
                Spacer(modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TaskListPreview() {
    TaskList(
        tasks = listOf(
            Task.create(title = "Send email to research team at Morning"),
            Task.create(title = "Drink Water"),
            Task.create(title = "Drink Water"),
            Task.create(title = "Drink Water"),
            Task.create(title = "Drink Water"),
            Task.create(title = "Drink Water"),
            Task.create(title = "Drink Water"),
            Task.create(title = "Drink Water"),
            Task.create(title = "Drink Water"),
            Task.create(title = "Drink Water"),
        )
    )
}

@Composable
fun TaskDetailButtons(
    modifier: Modifier = Modifier,
    onSave: () -> Unit = {},
    onDelete: () -> Unit = {},
    onCancel: () -> Unit = {},
    isEdit: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color=MaterialTheme.colorScheme.background,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically,
    ) {
        TextButton(
            onClick = {
                onDelete()
            },
            enabled = isEdit,
        )
        {
            Text(
                text = "Delete",
                color = MaterialTheme.colorScheme.error,
            )
        }
        TextButton(
            onClick = {
                onCancel()
            },
        ) {
            Text(
                text = "Cancel",
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
        TextButton(onClick = {
            onSave()
        }) {
            Text(
                text = "Save"
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TaskDetailButtonsPreview() {
    TaskDetailButtons()
}


@Composable
fun TaskDetailModal(
    modifier: Modifier = Modifier,
    task: Task? = null,
    onSave: (Task) -> Unit = {},
    onDelete: (Task?) -> Unit = {},
    onCancel: () -> Unit = {},
    unsavedTextState: MutableState<String> = rememberSaveable {
        mutableStateOf(task?.title ?: "")
    }
) {
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = modifier
            .imePadding(),
        verticalArrangement = Arrangement.Bottom
    ) {
        TextField(
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .weight(0.9f),
            value = unsavedTextState.value,
            textStyle = MaterialTheme.typography.headlineSmall,
            onValueChange = {
                unsavedTextState.value = it
            }
        )
        TaskDetailButtons(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f),
            isEdit = task != null,
            onSave = {
                if (task != null) {
                    onSave(task.copy(title = unsavedTextState.value))
                } else {
                    onSave(Task.create(title = unsavedTextState.value))
                }
                unsavedTextState.value = ""
            },
            onDelete = {
                onDelete(task)
                unsavedTextState.value = ""
            },
            onCancel = {
                onCancel()
                unsavedTextState.value = ""
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TaskDetailModalPreview() {
    TaskDetailModal(
        task = Task.create(title = "Send email to research team at Morning")
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TaskDetailScaffold(
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    initialValue: SheetValue = SheetValue.Hidden,
    task: Task? = null,
    onSave: (Task) -> Unit = {},
    onDelete: (Task?) -> Unit = {},
    child: @Composable (
        BottomSheetScaffoldState,
        show: (Task?) -> Unit,
        hide: () -> Unit,
    ) -> Unit = { _, _, _ -> },
) {
    val currentTask = remember { mutableStateOf(task) }
    val textState = remember { mutableStateOf(task?.title ?: "") }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            initialValue = initialValue,
            confirmValueChange = {
                false
            }
        )
    )
    BottomSheetScaffold(
        modifier = modifier,
        sheetPeekHeight = 0.dp,
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            TaskDetailModal(
                task = currentTask.value,
                unsavedTextState = textState,
                onSave = {
                    currentTask.value = null
                    scope.launch {
                        keyboardController?.hide()
                        bottomSheetScaffoldState.bottomSheetState.hide()
                    }
                    onSave(it)
                },
                onDelete = {
                    currentTask.value = null
                    scope.launch {
                        keyboardController?.hide()
                        bottomSheetScaffoldState.bottomSheetState.hide()
                    }
                    onDelete(it)
                },
                onCancel = {
                    currentTask.value = null
                    scope.launch {
                        keyboardController?.hide()
                        bottomSheetScaffoldState.bottomSheetState.hide()
                    }
                }
            )
        }
    ) {
        child(bottomSheetScaffoldState, {
            scope.launch {
                currentTask.value = it
                textState.value = it?.title ?: ""
                bottomSheetScaffoldState.bottomSheetState.expand()
                if (it == null)
                    keyboardController?.show()
            }
        }, {
            scope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
        })
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun TaskDetailScaffoldPreview() {
}

