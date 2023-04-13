package com.ugnet.sel1.ui.components


import android.content.Context
import android.widget.Toast
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun ResidentTopBar(
    onNavigationIconClick: () -> Unit,
    topBarTitle: String
) {
    TopAppBar (
        title = { Text(text = topBarTitle, color = AccentLicht) },
        backgroundColor = MainGroen,
        contentColor = AccentLicht,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = Icons.Rounded.Menu,
                    contentDescription = "Toggle drawer",
                    tint = AccentLicht)
            }
        },
    )
}

fun showMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}