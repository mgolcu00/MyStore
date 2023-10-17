package com.mertgolcu.mystore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.mertgolcu.mystore.tasker.common.bottom.BottomBar
import com.mertgolcu.mystore.tasker.common.top.TopBar
import com.mertgolcu.mystore.tasker.screens.NavGraphs
import com.mertgolcu.mystore.tasker.screens.destinations.TaskDetailScreenDestination
import com.mertgolcu.mystore.tasker.screens.destinations.TaskScreenWithoutBottomSheetDestination
import com.mertgolcu.mystore.ui.theme.MyStoreTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // WindowCompat.setDecorFitsSystemWindows(window, false)
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

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun Main() {
    val navController = rememberNavController()
    val engine = rememberAnimatedNavHostEngine(
        defaultAnimationsForNestedNavGraph = mapOf(
            NavGraphs.root to NestedNavGraphDefaultAnimations(
                enterTransition = { fadeIn(animationSpec = tween(2000)) },
                exitTransition = { fadeOut(animationSpec = tween(2000)) }
            ),
        ))
    MainFrame(navController) {
        DestinationsNavHost(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .shadow(elevation = 0.1.dp, RoundedCornerShape(8.dp), clip = true)
                .padding(2.dp)
                .clip(RoundedCornerShape(8.dp)),
            navController = navController,
            navGraph = NavGraphs.root,
            engine = engine
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

    //val isKeyboardOpen by keyboardAsState() // true or false
    val currentDestination = navController.currentDestinationAsState()
    val currentBackStack = navController.currentBackStack.collectAsState()
    var visible by remember {
        mutableStateOf(false)
    }
    val density = LocalDensity.current
    // on current destination change
    LaunchedEffect(currentDestination.value) {
        visible = when (currentDestination.value) {
            is TaskScreenWithoutBottomSheetDestination -> {
                true
            }

            is TaskDetailScreenDestination -> {
                false
            }

            else -> {
                true
            }
        }
    }
    Scaffold(topBar = {
        TopBar(backStack = currentBackStack, onNavigateBack = {
            navController.popBackStack()
        })
    }, bottomBar = {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {

            BottomBar(navController = navController)
        }

    }, floatingActionButton = {
        if (currentDestination.value != TaskDetailScreenDestination)
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
                        navController.navigate(TaskDetailScreenDestination())
                    }

                    is TaskDetailScreenDestination -> {
                        navController.popBackStack()
                    }

                    else -> {
                        // do nothing
                    }
                }

            }
            )
    }) { paddingValues ->
        child(paddingValues)
    }
}

