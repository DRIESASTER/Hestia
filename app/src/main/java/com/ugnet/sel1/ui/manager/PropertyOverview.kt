package com.ugnet.sel1.ui.manager

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.ui.components.PropertyCard

@Composable
fun PropertyOverview(modifier: Modifier = Modifier,properties:List<PropertyData>,onPropertyClicked:(PropertyData)->Unit) {
    Surface(modifier = modifier) {
        LazyColumn (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
            itemsIndexed(properties) { _, property ->
                PropertyCard(
                    propName = property.name,
                    propAddress = property.address,
                    tennants = property.tenants.size,
                    issueCount = property.issues.size,
                    onClick = { onPropertyClicked(property) }
                )
                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    }
}