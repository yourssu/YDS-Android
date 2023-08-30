package com.yourssu.storybook.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

enum class Atoms {
    Badge,
    BottomSheet,
    BoxButton,
    Checkbox,
    ListItem,
    ListToggleItem,
    Picker,
    PlainButton,
    ProfileImageView,
    // TextField,
    Toggle,
}

//enum class Components {
//    Tooltip,
//    TopBar,
//    BottomBar,
//    Toast,
//    TabBar
//}

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = "main",
        modifier = modifier,
    ) {
        composable("main") {
            MainScreen(navController)
        }
        composable(Atoms.Badge.name) {

        }
        composable(Atoms.BottomSheet.name) {

        }
        composable(Atoms.BoxButton.name) {

        }
        composable(Atoms.Checkbox.name) {

        }
        composable(Atoms.ListItem.name) {

        }
        composable(Atoms.ListToggleItem.name) {

        }
        composable(Atoms.Picker.name) {

        }
        composable(Atoms.PlainButton.name) {

        }
        composable(Atoms.ProfileImageView.name) {

        }
        composable(Atoms.Toggle.name) {

        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {

}
