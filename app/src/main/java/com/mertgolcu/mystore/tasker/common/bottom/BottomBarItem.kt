package com.mertgolcu.mystore.tasker.common.bottom

import androidx.compose.runtime.Composable

/**
 * Created by Mert Gölcü on 11.10.2023
 */

data class BottomBarItem(
    val id:Int=0,
    val label : @Composable () -> Unit,
    val icon : @Composable () -> Unit,
    val action : () -> Unit,
)
