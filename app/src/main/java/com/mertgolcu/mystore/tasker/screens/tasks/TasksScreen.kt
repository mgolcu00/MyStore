package com.mertgolcu.mystore.tasker.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertgolcu.mystore.tasker.domain.model.Task
import kotlinx.coroutines.launch

/**
 * @author mertgolcu
 * @since 27.09.2023
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TasksScreen2() {
    val showAddTaskDialog = remember { mutableStateOf(false) }
    val taskListState = remember { mutableStateOf(tasks) }
    val showFloatingActionButton = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    var descState = remember { mutableStateOf(TextFieldValue("")) }
    var descFocusRequester = remember { FocusRequester() }
    var taskTextState = remember { mutableStateOf(TextFieldValue("")) }

    TaskAddModal(taskTextState.value.text) { state, keyboardController, focusRequester ->
        TaskList(modifier = Modifier,
            tasks = taskListState.value,
            expandListener = {it,text->
                showFloatingActionButton.value = !it
                scope.launch {
                    state.bottomSheetState.expand()
                    focusRequester.requestFocus()
                    keyboardController.show()
                    taskTextState.value = TextFieldValue(text)
                }
            },
            onClickAdd = {
                scope.launch {
                    state.bottomSheetState.expand()
                    focusRequester.requestFocus()
                    keyboardController.show()
                }
            }
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen() {
    val showAddTaskDialog = remember { mutableStateOf(false) }
    val taskListState = remember { mutableStateOf(tasks) }
    val showFloatingActionButton = remember { mutableStateOf(true) }
    val sheetState = remember {
        SheetState(
            false, initialValue = SheetValue.Hidden
        )
    }
    val scope = rememberCoroutineScope()
    var descState = remember { mutableStateOf(TextFieldValue("")) }
    var descFocusRequester = remember { FocusRequester() }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            false,
            initialValue = SheetValue.PartiallyExpanded,
        )
    )
    Scaffold(
        floatingActionButton = {
            if (showFloatingActionButton.value && !sheetState.isVisible) {
                FloatingActionButton(modifier = Modifier, onClick = {
                    scope.launch {


                        sheetState.expand()
                    }.invokeOnCompletion {
                        showAddTaskDialog.value = true

                    }
                }) {
                    Text(
                        modifier = Modifier.padding(8.dp), text = "+ Add Task"
                    )
                }
            }
        },
    ) { pv ->

        if (sheetState.isVisible) ModalBottomSheet(
            modifier = Modifier
                .imePadding()
                .offset(

                ),
            onDismissRequest = {
                showAddTaskDialog.value = false
                scope.launch { sheetState.hide() }
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
                /*Column {
                    Text(text = "Add New Task")
                    ExpandedTaskRow(task = Task.create(title = ""), onDone = {
                        taskListState.value = taskListState.value.toMutableList().apply {
                            add(it)
                        }

                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showAddTaskDialog.value = false
                            }
                        }
                    }, onCancel = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showAddTaskDialog.value = false
                            }
                        }
                    }, focus = false
                    )

                    Box {
                        IconButton(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showAddTaskDialog.value = false
                                }
                            }
                        }) {
                            Icon(
                                modifier = Modifier.padding(8.dp),
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(Color.Magenta)
                    ) {
                        OutlinedTextField(modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(descFocusRequester),
                            value = descState.value,
                            onValueChange = {
                                descState.value = it
                            })
                        LaunchedEffect(Unit) {
                            descFocusRequester.requestFocus()
                        }
                    }
                }*/
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Save")
                        }
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Cancel")
                        }
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Delete")
                        }
                    }
                    OutlinedTextField(modifier = Modifier
                        .fillMaxSize(0.8f)
                        .padding(16.dp)
                        .focusRequester(descFocusRequester),
                        value = descState.value,
                        onValueChange = {
                            // max lines
                            if (it.text.count { char -> char == '\n' } > 3) {
                                descState.value = descState.value.copy(
                                    text = it.text.substring(0, it.text.lastIndexOf('\n')),
                                    selection = TextRange(it.text.lastIndexOf('\n'))
                                )
                                return@OutlinedTextField
                            }
                            if (it.text.length > 100) {
                                descState.value = descState.value.copy(
                                    text = it.text.substring(0, 100),
                                    selection = TextRange(100)
                                )
                                return@OutlinedTextField
                            }
                            descState.value = it
                        },
                        supportingText = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Spacer(modifier = Modifier.padding(4.dp))
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = "${descState.value.text.length}/100"
                                )
                            }
                        }
                    )

                }
            }


        }
        else TaskList(modifier = Modifier.padding(pv),
            tasks = taskListState.value,
            expandListener = {it,text->
                showFloatingActionButton.value = !it
            })

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
    expandListener: (Boolean,String) -> Unit = {_,_->},
    onClickAdd: () -> Unit = {}
) {
    val tasksState = rememberSaveable { mutableStateOf(tasks) }
    val expandedIndexState = remember { mutableIntStateOf(-1) }
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
                onExpand = {it,title ->
                    if (!it) {
                        expandedIndexState.intValue = -1
                        expandListener(false,title)
                    } else {
                        expandedIndexState.intValue = index
                        expandListener(true,title)
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
                TextButton(onClick = {
                    onClickAdd()
                }) {
                    Text(text = "Add Task")
                }
                Spacer(modifier = Modifier.height(64.dp))
            }
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
fun ExpandedTaskRow(
    task: Task, onDone: (task: Task) -> Unit = {}, onCancel: () -> Unit = {}, focus: Boolean = true
) {
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
        if (focus) {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
                titleState.value = titleState.value.copy(
                    selection = TextRange(titleState.value.text.length)
                )
            }
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
                    focusRequester.freeFocus()
                    titleState.value = titleState.value.copy("")
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
                    focusRequester.freeFocus()
                    titleState.value = titleState.value.copy("")
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
    onExpand: (Boolean,String) -> Unit = {_,_->},
) {
    if (expanded) {
        ExpandedTaskRow(task = task, onDone = {
            onDone(it)
            onExpand(false,task.title)
        }, onCancel = {
            onExpand(false,task.title)
        })
    } else {
        CollapsedTaskTow(task = task, onClick = {
            onExpand(true,task.title)
        }, onCheckedChange = { task, isChecked ->
            onDone(task.updateCompleted(isChecked))
        })
    }

}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TaskBottomSheet(
    onDismiss: () -> Unit,
    onSaveTask: (String, String) -> Unit // Bu işlevi görevi kaydetmek için kullanabilirsiniz
) {
    var taskName by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val density = LocalDensity.current.density

    // Bottom Sheet'ın yüksekliği ve görünürlüğünü kontrol eder
    val state = rememberModalBottomSheetState()

    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = {
            onDismiss()
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Yeni Görev Ekle",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value = taskName,
                onValueChange = { taskName = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    onSaveTask(taskName, selectedDate)
                    coroutineScope.launch {
                        state.hide()
                    }
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tarih seçimi için bir DatePicker ekleyebilirsiniz
            // selectedDate değişkenini güncellemeyi unutmayın

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        onSaveTask(taskName, selectedDate)
                        coroutineScope.launch {
                            state.hide()
                        }
                    }, modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Kaydet")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            state.hide()
                        }
                    }, modifier = Modifier.weight(1f)
                ) {
                    Text(text = "İptal")
                }
            }
        }
    }


    val view = LocalView.current
    DisposableEffect(view) {
        onDispose {
            coroutineScope.launch {
                state.hide()
            }
        }
    }

    // Bottom Sheet'ı göstermek için bir düğme veya tetikleyici eklemeyi unutmayın
}


@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun TaskPreview() {
    //  TaskBottomSheet(onDismiss = { /*TODO*/ }, onSaveTask = { _, _ -> })
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
