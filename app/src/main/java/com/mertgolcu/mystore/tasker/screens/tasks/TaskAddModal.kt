package com.mertgolcu.mystore.tasker.screens.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertgolcu.mystore.tasker.domain.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author mertgolcu
 * @since 02.10.2023
 */


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TaskAddModal(
    text: String = "",
    child: @Composable (
        state: BottomSheetScaffoldState,
        keyboardController: SoftwareKeyboardController,
        focusRequester: FocusRequester,

        ) -> Unit = { _, _, _ -> }
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    var focusRequester = remember { FocusRequester() }
    var scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            initialValue = SheetValue.Hidden,
            confirmValueChange = {
                return@SheetState when (it) {
                    SheetValue.Expanded -> {
                        keyboardController?.show()
                        focusRequester.requestFocus()
                        false
                    }

                    SheetValue.Hidden -> {
                        keyboardController?.hide()
                        focusRequester.freeFocus()
                        true
                    }

                    SheetValue.PartiallyExpanded -> {
                        keyboardController?.hide()
                        focusRequester.freeFocus()

                        false
                    }
                }
                false
            }
        ),
    )
    var textState = remember { mutableStateOf(text) }



    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Column(
                modifier = Modifier.imePadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Save")
                    }
                    Button(onClick = {
                        scope.launch {
                            keyboardController?.hide()
                        }.invokeOnCompletion {
                            scope.launch {
                                scaffoldState.bottomSheetState.partialExpand()
                                scaffoldState.bottomSheetState.hide()
                            }.invokeOnCompletion {

                            }
                        }
                    }) {
                        Text(text = "Cancel")

                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Delete")
                    }
                }
                TextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequester = focusRequester),
                    value = textState.value,
                    textStyle = MaterialTheme.typography.headlineMedium,
                    onValueChange = {
                        textState.value = it
                    })
            }
        },

        ) {
        keyboardController?.let { it1 -> child(scaffoldState, it1, focusRequester) }

    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddAndEditTaskModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onDone: (task: Task) -> Unit = {},
    onDelete: (task: Task) -> Unit = {},
    initialState: SheetValue = SheetValue.Hidden,
    child: @Composable (
        state: BottomSheetScaffoldState,
        keyboardController: SoftwareKeyboardController?,
        focusRequester: FocusRequester,
        scope: CoroutineScope,
        onTaskChange: (task: Task) -> Unit,
    ) -> Unit = { _, _, _, _, _ -> },
) {
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var currentTask = remember { mutableStateOf(Task.create(title = "")) }
    val textState = remember { mutableStateOf("") }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            initialValue = initialState,
            confirmValueChange = {
                false
            }
        )
    )
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .imePadding(),
                verticalArrangement = Arrangement.Bottom,

                ) {
                TextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequester = focusRequester)
                        .weight(0.9f),
                    value = textState.value,
                    textStyle = MaterialTheme.typography.headlineSmall,
                    onValueChange = {
                        textState.value = it
                    },
                )
                ModalControlPanel(
                    modifier = Modifier.weight(0.1f),
                    onDone = {
                        currentTask.value?.let {
                            onDone(it.copy(title = textState.value))
                            textState.value = ""
                        }
                        scope.launch {
                            keyboardController?.hide()
                            bottomSheetScaffoldState.bottomSheetState.hide()
                        }
                    },
                    onDelete = {
                        currentTask.value?.let {
                            onDelete(it)
                            textState.value = ""
                        }
                        scope.launch {
                            keyboardController?.hide()
                            bottomSheetScaffoldState.bottomSheetState.hide()
                        }
                    },
                    onDismiss = {
                        textState.value = ""
                        scope.launch {
                            keyboardController?.hide()
                            bottomSheetScaffoldState.bottomSheetState.hide()
                        }
                    },
                )
            }
        }
    ) {
        child(bottomSheetScaffoldState,
            keyboardController,
            focusRequester,
            scope,
            onTaskChange = {
                currentTask.value = it
                textState.value = it.title
            })
    }


}

@Composable
fun ModalControlPanel(
    modifier: Modifier = Modifier,
    onDone: () -> Unit = {},
    onDelete: () -> Unit = {},
    onDismiss: () -> Unit = {},
    isEdit: Boolean = false,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
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
        TextButton(onClick = {
            onDismiss()
        }) {
            Text(
                text = "Cancel",
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
        TextButton(onClick = {
            onDone()
        }) {
            Text(
                text = "Save"
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
@Preview(showBackground = true)
fun TaskAddModalPreview() {
    // TaskAddModal()
    AddAndEditTaskModal(
        initialState = SheetValue.Expanded
    )
}