package com.mertgolcu.mystore.tasker.screens.task.detail

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.mertgolcu.mystore.data.local.entities.Task
import com.mertgolcu.mystore.tasker.common.loading.shimmer
import com.mertgolcu.mystore.tasker.screens.appDestination
import com.mertgolcu.mystore.tasker.screens.destinations.HomeScreenDestination
import com.mertgolcu.mystore.tasker.screens.destinations.TaskDetailScreenDestination
import com.mertgolcu.mystore.tasker.screens.destinations.TaskScreenWithoutBottomSheetDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.ramcosta.composedestinations.utils.destination
import kotlinx.coroutines.delay
import java.time.LocalDateTime

/**
 * Created by Mert Gölcü on 11.10.2023
 */


const val DURATION = 300
val DIRECTION = "Y"

object TaskDetailTransitions : DestinationStyle.Animated {
    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return when (initialState.appDestination()) {
            is TaskDetailScreenDestination -> {
                slideInVertically(
                    initialOffsetY = { 2000 },
                    animationSpec = tween(DURATION)
                )
            }

            HomeScreenDestination -> {
                slideInHorizontally(
                    initialOffsetX = { -1000 },
                    animationSpec = tween(DURATION)
                )
            }

            TaskScreenWithoutBottomSheetDestination -> {
                if (initialState.destination().route == "taskDetailScreen") {
                    slideInVertically(
                        initialOffsetY = { 2000 },
                        animationSpec = tween(DURATION)
                    )
                } else {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(DURATION)
                    )
                }
            }
        }


    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition? {

        return when (initialState.appDestination()) {
            is TaskDetailScreenDestination -> {
                slideOutVertically(
                    targetOffsetY = { -2000 },
                    animationSpec = tween(DURATION)
                )
            }

            HomeScreenDestination -> {
                slideOutHorizontally(
                    targetOffsetX = { 1000 },
                    animationSpec = tween(DURATION)
                )
            }

            TaskScreenWithoutBottomSheetDestination -> {
                if (initialState.destination().route == "taskDetailScreen") {
                    slideOutVertically(
                        targetOffsetY = { -2000 },
                        animationSpec = tween(DURATION)
                    )
                } else {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(DURATION)
                    )
                }
            }
        }
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return when (initialState.appDestination()) {
            is TaskDetailScreenDestination -> {
                slideInVertically(
                    initialOffsetY = { -2000 },
                    animationSpec = tween(DURATION)
                )
            }

            HomeScreenDestination -> {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(DURATION)
                )
            }

            TaskScreenWithoutBottomSheetDestination -> {
                if (initialState.destination().route == "taskDetailScreen") {
                    slideInVertically(
                        initialOffsetY = { -2000 },
                        animationSpec = tween(DURATION)
                    )
                } else {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(DURATION)
                    )
                }
            }
        }
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return when (initialState.appDestination()) {
            is TaskDetailScreenDestination -> {
                slideOutVertically(
                    targetOffsetY = { 2000 },
                    animationSpec = tween(DURATION)
                )
            }

            HomeScreenDestination -> {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(DURATION)
                )
            }

            TaskScreenWithoutBottomSheetDestination -> {
                if (initialState.destination().route == "taskDetailScreen") {
                    slideOutVertically(
                        targetOffsetY = { 2000 },
                        animationSpec = tween(DURATION)
                    )
                } else {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(DURATION)
                    )
                }
            }
        }
    }

}

/**
 *  modifier: Modifier = Modifier,
 *     navigator: DestinationsNavigator? = null,
 *     viewModel: TaskViewModel = hiltViewModel(),
 */
@Destination(
    route = "taskDetailScreen",
    style = TaskDetailTransitions::class,
)
@Composable
fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    taskId: Int? = null,
    navigator: DestinationsNavigator? = null,
    viewModel: TaskDetailViewModel = hiltViewModel(),

    ) {
    val state by viewModel.state

    when (state) {
        TaskDetailState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
            ) {
               Column(
                   modifier=Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
               ) {
                   Text(
                       modifier = Modifier
                           .fillMaxWidth(0.7f)
                           .shimmer(
                               shape = RoundedCornerShape(8.dp)
                           ), text = ""
                   )
                   Spacer(modifier = Modifier.padding(8.dp))
                   Text(
                       modifier = Modifier
                           .fillMaxWidth(0.4f)
                           .shimmer(
                               shape = RoundedCornerShape(8.dp)
                           ), text = ""
                   )
               }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .width(72.dp)
                        .height(64.dp)
                        .padding(16.dp)
                        .shimmer(
                            shape = RoundedCornerShape(8.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .width(128.dp)
                        .height(64.dp)
                        .padding(16.dp)
                        .shimmer(
                            shape = RoundedCornerShape(8.dp)
                        )
                )
            }
            LaunchedEffect(Unit) {
                delay(3000)
                viewModel.processIntent(TaskDetailIntent.LoadTask(taskId))
            }
        }

        is TaskDetailState.Error -> {
            Text(text = "Error")
        }

        is TaskDetailState.Success -> {
            TaskDetailContent(
                modifier = modifier,
                task = (state as TaskDetailState.Success).task,
                onClickCancel = {
                    navigator?.navigateUp()
                },
                onClickDelete = { task ->
                    task?.let {
                        viewModel.processIntent(TaskDetailIntent.DeleteTask(it))
                    }
                    navigator?.navigateUp()
                },
                onClickSave = {
                    viewModel.processIntent(TaskDetailIntent.UpdateTask(it))
                    navigator?.navigateUp()
                },
                onClickCreate = {
                    viewModel.processIntent(TaskDetailIntent.CreateTask(it))
                    navigator?.navigateUp()
                }
            )
        }

    }

}

@Composable
fun TaskDetailContent(
    modifier: Modifier = Modifier,
    task: Task? = null,
    onClickSave: (Task) -> Unit = {},
    onClickCreate: (String) -> Unit = {},
    onClickDelete: (Task?) -> Unit = {},
    onClickCancel: () -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    val textState = remember { mutableStateOf(task?.title ?: "") }
    val isEdit = task != null
    // make this colum to scrollable
    Box(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
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
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {

            ExtendedFloatingActionButton(
                modifier = Modifier.align(CenterVertically),
                onClick = {
                    // on Cancel
                    onClickCancel()
                },
                contentColor = MaterialTheme.colorScheme.secondary,
            ) {
                Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
            }
            if (isEdit) {
                Spacer(modifier = Modifier.padding(8.dp))
                ExtendedFloatingActionButton(
                    modifier = Modifier.align(CenterVertically),
                    onClick = {
                        // on delete
                        onClickDelete(task!!)
                    },
                    contentColor = MaterialTheme.colorScheme.error,
                ) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                }
            }
            //Spacer(modifier = Modifier.padding(8.dp))
        }
        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                // on save
                if (isEdit) {
                    onClickSave(
                        task!!.copy(
                            title = textState.value,
                            updatedAt = LocalDateTime.now()
                        )
                    )
                } else {
                    onClickCreate(textState.value)
                }
            }) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null)
            Text(text = if (isEdit) "Save" else "Create")
        }


    }

}

@Composable
@Preview(showBackground = true)
fun TaskDetailScreenPreview() {
    TaskDetailScreen()
}
