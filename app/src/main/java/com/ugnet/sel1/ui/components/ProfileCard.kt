package com.ugnet.sel1.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun ProfileCard(name: String, email: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MainGroen
    ) {
        Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
            Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 10.dp)){
                Icon(imageVector = Icons.Rounded.Person, contentDescription = "profile card",
                    tint = Color.White, modifier = Modifier.size(60.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.99f)
                    .padding(16.dp)
            ) {
                Row{
                    Icon(imageVector = Icons.Rounded.Badge, contentDescription = "name", tint = Color.White, modifier = Modifier.padding(5.dp))
                    Text(
                    text = name,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = Color.White
                )
                }
                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Rounded.Mail, contentDescription = "mail", tint = Color.White, modifier = Modifier.padding(5.dp) )
                    Text(
                        text = email,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun ProfileCardPreview() {
    ProfileCard(name = "John Doe", email = "johndoe@gmail.com")
}