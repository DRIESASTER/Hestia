package com.ugnet.sel1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            //.padding(vertical = 64.dp)
            .background(MainGroen),
        contentAlignment = Alignment.Center
    ) {
        Row() {
            Icon(imageVector = Icons.Rounded.Home, contentDescription = "Home icon", modifier = Modifier.size(60.dp), tint = AccentLicht)
            Text(text = "Hestia", fontSize = 40.sp, color = AccentLicht)
        }
    }
}

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit
) {
    LazyColumn(modifier) {
        items(items) {item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
            ) {
                Icon(imageVector = item.icon, contentDescription = null)
                Spacer(modifier = modifier.width(16.dp))
                Text(text = item.name,style = itemTextStyle, modifier = Modifier.weight(1f))
            }
        }
    }
}