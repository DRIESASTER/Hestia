package com.ugnet.sel1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun PropertyCard(propName: String,
                 propAddress: String,
                 tennants:Int,
                 issueCount:Int,
                 onClick: () -> Unit,
                 modifier: Modifier = Modifier,onDelete: () -> Unit,
                 onEdit: () -> Unit ) {

    Card(
        modifier = modifier
            .padding(10.dp)
            .background(Color.Transparent)
            .clip(RoundedCornerShape(15.dp))
            .clickable(onClick = onClick)
            .wrapContentWidth(), contentColor = Color.Transparent
    ) {
        //everythingcontainer
        Row(
            modifier = Modifier
                .background(MainGroen)
                .clip(RoundedCornerShape(10.dp))
                .wrapContentWidth()
                .height(120.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(250.dp)
                    .padding(10.dp)
            ) {
                //name
                Row() {
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = propName, color = AccentLicht, modifier = Modifier.padding(1.dp))

                }
                //address
                Spacer(modifier =Modifier.height(3.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .wrapContentSize()
                        .background(AccentLicht)
                        .padding(horizontal = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = "person",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(2.dp)
                            .size(12.dp)
                    )
                    Text(
                        text = propAddress,
                        color = Color.Black,
                        style = MaterialTheme.typography.body1,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(2.dp)
                    )
                }
                Spacer(modifier =Modifier.height(3.dp))
                //tennants
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(AccentLicht)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "person",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(2.dp)
                            .size(12.dp)
                    )
                    Text(
                        text = "tenants: $tennants",
                        color = Color.Black,
                        style = MaterialTheme.typography.body1,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(2.dp)
                    )
                }
                Spacer(modifier =Modifier.height(3.dp))
                //issuecount
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(AccentLicht)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PriorityHigh,
                        contentDescription = "person",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(3.dp)
                            .size(12.dp)
                    )
                    Text(
                        text = "issues: $issueCount",
                        color = Color.Black,
                        style = MaterialTheme.typography.body1,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(1.dp)
                    )
                }

            }
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                IconButton(onClick = onEdit ) {
                    Icon (imageVector = Icons.Rounded.Edit,
                        contentDescription = "edit",
                        tint = AccentLicht,
                        modifier = Modifier
                            .padding(3.dp)
                            .size(30.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Rounded.Delete, contentDescription ="remove",
                    tint = AccentLicht,modifier = Modifier.padding(3.dp).size(30.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun PropertyCardPreview() {
    PropertyCard(propName = "test", propAddress = "test", tennants = 1, issueCount = 1, onClick = {}, modifier = Modifier, onDelete = {}, onEdit = {})
}