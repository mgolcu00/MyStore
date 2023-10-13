package com.mertgolcu.mystore.tasker.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertgolcu.mystore.tasker.domain.model.Task
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Created by Mert Gölcü on 11.10.2023
 */

@Destination(
    route = "taskDetailScreen",
)
@Composable
fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    task: Task? = null,
    onClickSave: (Task) -> Unit = {},
    onClickDelete: (Task) -> Unit = {},
    onClickCancel: () -> Unit = {},
    navigator: DestinationsNavigator? = null
) {
    val focusRequester = remember { FocusRequester() }
    val textState = remember { mutableStateOf(task?.title ?: "") }
    val isEdit = task != null
    // make this colum to scrollable
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        TextField(
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester),
            value = textState.value,
            textStyle = MaterialTheme.typography.headlineSmall,
            onValueChange = {
                textState.value = it
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TaskDetailScreenPreview() {
    TaskDetailScreen()
}
