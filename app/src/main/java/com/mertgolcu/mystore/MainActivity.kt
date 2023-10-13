package com.mertgolcu.mystore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mertgolcu.mystore.tasker.common.bottom.BottomBar
import com.mertgolcu.mystore.tasker.common.top.TopBar
import com.mertgolcu.mystore.tasker.screens.task.NavGraphs
import com.mertgolcu.mystore.tasker.screens.task.TaskScreen
import com.mertgolcu.mystore.tasker.screens.task.TaskScreenWithoutBottomSheet
import com.mertgolcu.mystore.tasker.screens.task.destinations.TaskDetailScreenDestination
import com.mertgolcu.mystore.tasker.screens.task.destinations.TaskScreenWithoutBottomSheetDestination
import com.mertgolcu.mystore.ui.theme.MyStoreTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.currentDestinationAsState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            MyStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface
                ) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()
    MainFrame(navController) {
        DestinationsNavHost(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .shadow(1.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp)),
            navController = navController,
            navGraph = NavGraphs.root
        )
    }
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

@Composable
fun MainFrame(
    navController: NavController,
    child: @Composable (PaddingValues) -> Unit,
) {

    val isKeyboardOpen by keyboardAsState() // true or false
    val currentDestination = navController.currentDestinationAsState()
    val currentBackStack = navController.currentBackStack.collectAsState()
    Scaffold(topBar = {
        TopBar(backStack = currentBackStack, onNavigateBack = {
            navController.popBackStack()
        })
    }, bottomBar = {
        if (!isKeyboardOpen) {
            BottomBar(navController = navController)
        }
    }, floatingActionButton = {
        ExtendedFloatingActionButton(text = {
            when (currentDestination.value) {
                is TaskScreenWithoutBottomSheetDestination -> {
                    Text(text = "Add Task")
                }

                is TaskDetailScreenDestination -> {
                    Text(text = "Save Task")
                }

                else -> {
                    Text(text = "Else")
                }
            }


        }, icon = {
            when (currentDestination.value) {
                is TaskScreenWithoutBottomSheetDestination -> {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }

                is TaskDetailScreenDestination -> {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                }

                else -> {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = null)
                }
            }

        }, onClick = {
            when (currentDestination.value) {
                is TaskScreenWithoutBottomSheetDestination -> {
                    navController.navigate(TaskDetailScreenDestination)
                }

                is TaskDetailScreenDestination -> {
                    navController.popBackStack()
                }

                else -> {
                    // do nothing
                }
            }

        })
    }) {
        child(it)
    }
}

