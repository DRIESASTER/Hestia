package com.ugnet.sel1.ui.components

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import android.R
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import com.ugnet.sel1.ui.theme.AccentLicht

@Composable
fun ResidentDrawerMenu() {
    

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(126.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = Icons.Rounded.Home,
                contentDescription = "Drawer Icon",
                tint = AccentLicht,
                modifier = Modifier.size(128.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview
@Composable
fun ResidentDrawerMenuPreview() {
    ResidentDrawerMenu()
}