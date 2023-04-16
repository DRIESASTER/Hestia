package com.ugnet.sel1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PriorityHigh
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
                 modifier: Modifier = Modifier ) {

    Card(
        modifier = modifier
            .padding(10.dp)
            .background(Color.Transparent)
            .clip(RoundedCornerShape(15.dp))
            .wrapContentWidth(), contentColor = Color.Transparent
    ) {
        //everythingcontainer
        Row(
            modifier = Modifier
                .background(MainGroen)
                .clip(RoundedCornerShape(10.dp))
                .wrapContentWidth()
                .height(105.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(200.dp)
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
                        .background(AccentLicht).padding(horizontal = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = "person",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(2.dp)
                            .size(10.dp)
                    )
                    Text(
                        text = propAddress,
                        color = Color.Black,
                        style = MaterialTheme.typography.body1,
                        fontSize = 10.sp,
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
                            .size(10.dp)
                    )
                    Text(
                        text = "tenants: $tennants",
                        color = Color.Black,
                        style = MaterialTheme.typography.body1,
                        fontSize = 10.sp,
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
                            .size(10.dp)
                    )
                    Text(
                        text = "issues: $issueCount",
                        color = Color.Black,
                        style = MaterialTheme.typography.body1,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(1.dp)
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun PropertyCardPreview() {
    PropertyCard(propName = "test", propAddress = "test", tennants = 1, issueCount = 1, onClick = {})
}