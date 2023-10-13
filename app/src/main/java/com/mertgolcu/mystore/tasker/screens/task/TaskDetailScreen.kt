package com.mertgolcu.mystore.tasker.screens.task

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.mertgolcu.mystore.tasker.domain.model.Task
import com.mertgolcu.mystore.tasker.screens.appDestination
import com.mertgolcu.mystore.tasker.screens.destinations.HomeScreenDestination
import com.mertgolcu.mystore.tasker.screens.destinations.TaskDetailScreenDestination
import com.mertgolcu.mystore.tasker.screens.destinations.TaskScreenWithoutBottomSheetDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.utils.destination

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

@Destination(
    route = "taskDetailScreen",
    style = TaskDetailTransitions::class,
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
                },
                contentColor = MaterialTheme.colorScheme.secondary,
            ) {
                Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.padding(8.dp))
            ExtendedFloatingActionButton(
                modifier = Modifier.align(CenterVertically),
                onClick = {
                    // on delete
                },
                contentColor = MaterialTheme.colorScheme.error,
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
            }
            //Spacer(modifier = Modifier.padding(8.dp))
        }
        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                // on save
            }) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null)
            Text(text = "Save")
        }


    }
}

@Composable
@Preview(showBackground = true)
fun TaskDetailScreenPreview() {
    TaskDetailScreen()
}
