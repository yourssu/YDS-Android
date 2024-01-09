package com.yourssu.design.system.compose.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


// Define the Profile composable.
@Composable
fun Profile(navController: NavController) {
    Column {
        Text("Profile")
        Button(onClick = { }) {
            Text("Go to Friends List")
        }
    }
}

// Define the FriendsList composable.
@Composable
fun FriendsList(navController: NavController) {
    Column {
        Text("Friends List")
        Button(onClick = { }) {
            Text("Go to Profile")
        }
    }
}

sealed class Screen(val route: String) {
    object Profile : Screen("profile")
    object FriendsList : Screen("friendslist")
}

@Preview
@Composable
fun PreviewMain() {
    val navController = rememberNavController()

    val items = listOf(
        Screen.Profile,
        Screen.FriendsList,
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Profile.route,
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.Profile.route) { Profile(navController) }
            composable(Screen.FriendsList.route) { FriendsList(navController) }
        }
    }

}