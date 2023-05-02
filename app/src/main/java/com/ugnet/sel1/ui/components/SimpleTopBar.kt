package com.ugnet.sel1.ui.components

import androidx.compose.foundation.background
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun SimpleTopBar(name:String, navigate : (String) -> Unit) {
    TopAppBar(modifier = Modifier.background(MainGroen),
        title = { Text(text = name, color = Color.White) },
        contentColor = MainGroen,
        backgroundColor = MainGroen,
        navigationIcon = {
            IconButton(onClick = { navigate(MyDestinations.MANAGER_HOME_ROUTE) },Modifier.background(MainGroen)) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back", tint = AccentLicht)
            }
        }
    )
}


