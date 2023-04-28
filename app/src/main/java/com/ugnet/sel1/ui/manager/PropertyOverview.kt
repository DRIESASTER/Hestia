package com.ugnet.sel1.ui.manager

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.ui.components.PropertyCard
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun PropertiesOverview(viewModel:ManagerHomeVM) {
//    ownedPropertiesResponseFormatted = Response.Loading
    when(val response = viewModel.ownedPropertiesResponse){
        is Response.Success -> {
            if (response.data.isEmpty()) {
                Text(text = "No properties found")
            } else {
                PropertyOverview(
                    properties = response.data,
                    onPropertyClicked = {/*route to details*/ },
                    viewModel = viewModel,
                    onEditClicked = { /*route to edit*/ })
            }
        }
        else -> {
            CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
        }
    }
}


@Composable
fun PropertyOverview(modifier: Modifier = Modifier, properties:List<Property>, onPropertyClicked:(Property)->Unit, viewModel:ManagerHomeVM, onEditClicked:(Property)->Unit) {
    Surface(modifier = modifier) {
        LazyColumn (horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()){
            itemsIndexed(properties) { _, property ->
                viewModel.getIssuesPerProperty(property.propertyId!!).collectAsState(initial = Response.Loading).value.let {
                    when (it) {
                        is Response.Success -> {
                            var issueCount = 0
                            if (it.data.isNotEmpty()) {
                                issueCount = it.data.size
                            }
                            viewModel.getIssuesPerProperty(property.propertyId!!).collectAsState(
                                initial = Response.Loading
                            )
                            PropertyCard(
                                propName = property.straat!!,
                                propAddress = property.straat!!+" "+property.huisnummer!!+", "+property.postcode!!+" "+property.stad!!,
                                tennants = property.huurders.size,
                                issueCount = issueCount,
                                onClick = { onPropertyClicked(property) },
                                onDelete = { viewModel.removeProperty(property.propertyId!!) },
                                onEdit = { onEditClicked(property)}
                            )
                        }
                        else -> {
                            CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(0.dp))
            }
        }

    }
}