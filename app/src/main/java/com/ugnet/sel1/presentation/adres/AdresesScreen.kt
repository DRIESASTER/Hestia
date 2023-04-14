package com.ugnet.sel1.presentation.adres

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.ugnet.sel1.domain.repository.Adreses
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Response

@Composable
fun AdresesScreen(
    viewModel: AdresTestVM = hiltViewModel()
) {
    var openDialog by remember { mutableStateOf(false) }

    Scaffold(
        content = { padding ->
            Adreses(
                adresesContent = { adreses ->
                    AdresesContent(
                        padding = padding,
                        adreses = adreses
                    )
                }
            )
        }
    )
}

@Composable
fun AddBookAlertDialog(closeDialog: () -> Unit, addBook: Any) {
    TODO("Not yet implemented")
}

@Composable
fun TopBar() {
    TODO("Not yet implemented")
}

@Composable
fun Adreses(
    viewModel: AdresTestVM = hiltViewModel(),
    adresesContent: @Composable (adreses: Adreses) -> Unit
) {
    when(val adresesResponse = viewModel.adresesResponse) {
        is Response.Loading -> Text(text = "Loading")
        is Response.Success -> adresesContent(adresesResponse.data)
        is Response.Failure -> print(adresesResponse.e)
    }
}

@Composable
fun AdresesContent(
    padding: PaddingValues,
    adreses: Adreses
    ) {
        Text(text = "Adressen:")
        Text(text = "\n" + adreses[0].straat + " " + adreses[0].huisnummer + "\n")
}




