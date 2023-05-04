package com.ugnet.sel1.ui.manager.properties

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Response


@Composable
fun PropertyDetailRoute(
    viewModel: PropertyDetailVM,
    navigateBack: () -> Unit,
    navigate : (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is Response.Loading -> CircularProgressIndicator()
        is Response.Failure -> Text(text = "Failed")
        is Response.Success -> {
            val propertyData = (uiState as Response.Success<Property?>).data
            if (propertyData != null) {
                PropertyDetailsScreen(
                    propertyData,
                    viewModel = viewModel,
                    navigateBack = navigateBack
                )
            } else {
                Text(text = "Property not found")
            }
        }
    }
}


@Composable
fun PropertyDetailsScreen(
    property: Property,
    viewModel: PropertyDetailVM,
    navigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = property.stad!!, style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = property.straat!!, style = MaterialTheme.typography.subtitle1)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = property.huisnummer.toString(), style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = navigateBack,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Go Back")
        }
    }
}