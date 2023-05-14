package com.ugnet.sel1.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.domain.models.Property

@Composable
fun PropertyList(
    modifier: Modifier = Modifier,
    properties: List<Property>,
    onPropertyClicked: (Property) -> Unit
) {
    Surface(modifier = modifier) {
        LazyColumn(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(properties) { _, property ->
                Text(
                    text = property.stad!! + property.straat!! + property.huisnummer,
                    modifier = Modifier.padding(8.dp).clickable { onPropertyClicked(property) },  // Call onPropertyClicked when a property is clicked
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(0.dp))
    }
}