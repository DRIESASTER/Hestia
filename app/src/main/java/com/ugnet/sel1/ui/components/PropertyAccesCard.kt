package com.ugnet.sel1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Room
import com.ugnet.sel1.domain.repository.Rooms
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun PropertyAccesCard(property: Property,accessibleRooms:Rooms) {
    Card(backgroundColor = MainGroen, modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight().padding(vertical = 5.dp, horizontal = 6.dp)
        .clip(RoundedCornerShape(20.dp))) {
        Row(modifier = Modifier.padding(5.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = property.straat!! + " " + property.huisnummer!!,
                color = AccentLicht,
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(3.dp))
            Row() {
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .wrapContentSize()
                        .background(AccentLicht)
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = "person",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(2.dp)
                            .size(15.dp)
                    )
                    Text(
                        text = property.straat!! + " " + property.huisnummer!! + ", " + property.postcode!! + " " + property.stad!!,
                        color = Color.Black,
                        style = MaterialTheme.typography.body1,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .wrapContentSize()
                        .background(AccentLicht)
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Group,
                        contentDescription = "tenants",
                        modifier = Modifier
                            .padding(2.dp)
                            .size(18.dp)
                    )
                    Text(
                        text = property.huurders.size.toString(),
                        color = Color.Black,
                        style = MaterialTheme.typography.body1,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(2.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.height(3.dp))
            Column(modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight()){
                Text(
                    text = "Accessible rooms:",
                    color = AccentLicht,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier.padding(3.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                if(accessibleRooms.isEmpty()){
                    Text(
                        text = "No accessible rooms, ask your landlord for help!",
                        color = AccentLicht,
                        style = MaterialTheme.typography.body2,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        modifier = Modifier.padding(3.dp)
                    )
                } else {
                    LazyColumn(modifier = Modifier.height(60.dp), content = {
                        items(accessibleRooms.size) {
                            Row(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(30.dp)
                                    )
                                    .background(AccentLicht)
                                    .wrapContentWidth()
                                    .padding(horizontal = 3.dp, vertical = 1.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Key,
                                    contentDescription = "acces",
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = accessibleRooms[it].naam!!,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.body1,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    })
                }
            }

        }
    }
    }
}

@Preview
@Composable
fun PropertyAccesCardPreview() {
    Column() {

        PropertyAccesCard(
            Property(
                "Gent",
                "ijskelderstraat",
                92,
                9000,
                "1",
                "1",
                "1",
                listOf("1"),
            ),
            listOf(
                Room(
                    "1",
                    "1",
                    listOf("1"),
                ),
                Room(
                    "2",
                    "2",
                    listOf(),
                )
            )
        )
        PropertyAccesCard(
            Property(
                "Gent",
                "ijskelderstraat",
                92,
                9000,
                "1",
                "1",
                "1",
                listOf("1"),
            ),
            listOf()
        )
    }
}