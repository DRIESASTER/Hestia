package com.ugnet.sel1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun SimpleTopBar(name:String, icon: ImageVector? =null, navigate: (String) -> Unit, dest: String = MyDestinations.MANAGER_HOME_ROUTE) {
    TopAppBar(modifier = Modifier.background(MainGroen),
        title = {
            Row {
                if(icon!=null){
                    Icon(imageVector = icon, contentDescription = "extra info", tint = Color.White)
                }
                Text(text = name, color = Color.White)

            }
                },
        contentColor = MainGroen,
        backgroundColor = MainGroen,
        navigationIcon = {
            IconButton(onClick = { navigate(dest) },Modifier.background(MainGroen)) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back", tint = AccentLicht)
            }
        }
    )
}


