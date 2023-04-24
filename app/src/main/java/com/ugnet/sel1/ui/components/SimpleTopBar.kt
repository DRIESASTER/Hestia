package com.ugnet.sel1.ui.components

import androidx.compose.foundation.background
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun SimpleTopBar(name:String,openAndPopup: (String, String) -> Unit) {
    TopAppBar(
        title = { Text(text = name, color = Color.White) },
        navigationIcon = {
            IconButton(onClick = { openAndPopup(MyDestinations.EDIT_ROOMS,MyDestinations.MANAGER_HOME_ROUTE) },color= MainGroen) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back", tint = AccentLicht)
            }
        }
    )
}


