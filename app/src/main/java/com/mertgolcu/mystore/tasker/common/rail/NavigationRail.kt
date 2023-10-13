package com.mertgolcu.mystore.tasker.common.rail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Created by Mert Gölcü on 11.10.2023
 */

data class CustomNavigationRailItem(
    val id: Int = 0,
    val label: @Composable () -> Unit,
    val icon: @Composable () -> Unit,
    val action: (Int) -> Unit,
)

val items = listOf(
    CustomNavigationRailItem(
        id = 0,
        label = {
            Text(text = "Tasks")
        },
        icon = {
            Icon(imageVector = Icons.Default.List, contentDescription = null)
        },
        action = {
            //  navigate to tasks
        }
    ),
    CustomNavigationRailItem(
        id = 1,
        label = {
            Text(text = "Notes")
        },
        icon = {
            Icon(imageVector = Icons.Default.Build, contentDescription = null)
        },
        action = {
            //  navigate to Notes
        }
    ),
    CustomNavigationRailItem(
        id = 2,
        label = {
            Text(text = "Reminders")
        },
        icon = {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
        },
        action = {
            //  navigate to Reminders
        }
    ),
    CustomNavigationRailItem(
        id = 3,
        label = {
            Text(text = "Trackers")
        },
        icon = {
            Icon(imageVector = Icons.Filled.Face, contentDescription = null)
        },
        action = {
            //  navigate to Settings
        }
    ),
)

@Composable
fun CustomNavigationRail(
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        contentColor = MaterialTheme.colorScheme.onSurface,
        header = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }
    ) {
        items.forEachIndexed { index, customNavigationRailItem ->
            NavigationRailItem(
                selected = index == selectedIndex,
                onClick = {
                    selectedIndex = index
                    customNavigationRailItem.action(customNavigationRailItem.id)
                },
                icon = customNavigationRailItem.icon,
                label = customNavigationRailItem.label
            )
        }

    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CustomNavigationRailPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        CustomNavigationRail()
    }
}