package com.yourssu.storybook.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yourssu.design.system.compose.atom.BoxButton
import com.yourssu.storybook.compose.atom.BoxButtonScreen

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
    Toggle;
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
            BoxButtonScreen(navController)
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
    BoxButton(
        onClick = { navController.navigate(Atoms.BoxButton.name) },
        text = "테스트"
    )
}
