package com.mertgolcu.mystore.tasker.common.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * Created by Mert Gölcü on 11.10.2023
 */

val navBarItems = listOf(
    BottomBarItem(
        id = 0,
        label = {
            BottomBarItemLabel(text = "Tasks")
        },
        icon = {
            BottomBarItemIcon(imageVector = Icons.Default.Check)
        },
        action = {
            //  navigate to tasks
        }
    ),
    BottomBarItem(
        id = 1,
        label = {
            BottomBarItemLabel(text = "Notes")
        },
        icon = {
            BottomBarItemIcon(imageVector = Icons.Default.List)
        },
        action = {
            //  navigate to Notes
        }
    ),
    BottomBarItem(
        id = 2,
        label = {
            BottomBarItemLabel(text = "Reminders")
        },
        icon = {
            BottomBarItemIcon(imageVector = Icons.Default.Notifications)
        },
        action = {
            //  navigate to Reminders
        }
    ),
    BottomBarItem(
        id = 3,
        label = {
            BottomBarItemLabel(text = "Analytics")
        },
        icon = {
            BottomBarItemIcon(imageVector = Icons.Default.Info)
        },
        action = {
            //  navigate to Analytics
        }
    ),
)

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    var selectedItemState by remember {
        mutableIntStateOf(0)
    }
    NavigationBar(
        modifier = modifier
            .clip(
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {
        navBarItems.forEachIndexed { index, bottomBarItem ->
            NavigationBarItem(
                selected = selectedItemState == index,
                icon = bottomBarItem.icon,
                label = bottomBarItem.label,
                onClick = {
                    selectedItemState = index
                    when(index){
                        0->{
                            navController?.navigate("tasks")
                        }
                        1->{
                            navController?.navigate("home")
                        }
                    }
                    bottomBarItem.action()
                }
            )
        }
    }
}

@Composable
fun BottomBarItemLabel(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(modifier = modifier, text = text)
}

@Composable
fun BottomBarItemIcon(
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    bitmap: ImageBitmap? = null,
    imageVector: ImageVector? = null,
    contentDescription: String? = null,
) {
    when {
        painter != null -> {
            Icon(modifier = modifier, painter = painter, contentDescription = contentDescription)
        }

        bitmap != null -> {
            Icon(modifier = modifier, bitmap = bitmap, contentDescription = contentDescription)
        }

        imageVector != null -> {
            Icon(
                modifier = modifier,
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        }

        else -> {
            Icon(
                modifier = modifier,
                imageVector = Icons.Default.Info,
                contentDescription = contentDescription
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun BottomBarPreview() {
    Column(
        verticalArrangement = Arrangement.Bottom
    ) {
        BottomBar()
    }
}