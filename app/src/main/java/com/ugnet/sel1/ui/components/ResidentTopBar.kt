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
fun ResidentTopBar(barTitle: String) {
    val context = LocalContext.current

    TopAppBar (
        title = { Text(text = barTitle, color = AccentLicht) },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Rounded.Menu,
                    contentDescription = "Drawer Icon",
                    tint = AccentLicht)
            }
        },
        actions = {

        },
        backgroundColor = MainGroen
    )
}

@Preview
@Composable
fun ResidentTopBarPreview() {
    ResidentTopBar("Events")
}


fun showMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}