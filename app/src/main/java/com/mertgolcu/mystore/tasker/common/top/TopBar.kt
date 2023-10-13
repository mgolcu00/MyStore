package com.mertgolcu.mystore.tasker.common.top

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.ramcosta.composedestinations.utils.isRouteOnBackStack

/**
 * Created by Mert Gölcü on 11.10.2023
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    backStack: State<List<NavBackStackEntry>>? = null,
    title: @Composable () -> Unit = {},
    onClickRightIcon: () -> Unit = {},
    onClickLeftIcon: () -> Unit = {},
    onNavigateBack : () -> Unit = {},
    onClickProfile : () -> Unit = {},
) {

    CenterAlignedTopAppBar(
        modifier = modifier.clip(
            RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
        ),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text(text = "CozyDo") },
        navigationIcon = {
            if ((backStack?.value?.size ?: 0) > 2) {
                IconButton(
                    onClick = onNavigateBack
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            } else {
                IconButton(
                    onClick = onClickProfile
                ) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                }
            }

        },
        actions = {
            IconButton(
                onClick = onClickRightIcon
            ) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TopBarPreview() {
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        TopBar()
    }
}