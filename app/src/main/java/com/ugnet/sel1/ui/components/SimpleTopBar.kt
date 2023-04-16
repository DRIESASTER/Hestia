package com.ugnet.sel1.ui.components

import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material.Text
import androidx.compose.material.Icon
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun SimpleTopBar(name:String,navController: NavController) {
    TopAppBar(
        title = { Text(text = name) },
        navigationIcon = if (navController.previousBackStackEntry != null) {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MainGroen
                    )
                }
            }
        } else {
            null
        }
    )
}


