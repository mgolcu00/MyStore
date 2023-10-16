package com.mertgolcu.mystore.tasker.screens.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.mertgolcu.mystore.tasker.screens.task.detail.DURATION
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle

/**
 * Created by Mert Gölcü on 11.10.2023
 */

object HomeTransitions : DestinationStyle.Animated {
    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition? {

        return slideInHorizontally(
            initialOffsetX = { 1000 },
            animationSpec = tween(DURATION)
        )
    }


    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        return slideOutHorizontally(
            targetOffsetX = { -1000 },
            animationSpec = tween(DURATION)
        )
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return slideInHorizontally(
            initialOffsetX = { -1000 },
            animationSpec = tween(DURATION)
        )
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return slideOutHorizontally(
            targetOffsetX = { 1000 },
            animationSpec = tween(DURATION)
        )
    }

}

@Composable
@Destination(
    route = "home",
    style = HomeTransitions::class
)
fun HomeScreen() {
    Column(
        modifier= Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp))
    ) {
        Text(text = "Home Screen")
    }
}