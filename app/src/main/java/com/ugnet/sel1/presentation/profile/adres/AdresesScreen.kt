package com.ugnet.sel1.presentation.profile.adres

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Response

@Composable
fun AdresesScreen(
    viewModel: AdresTestVM = hiltViewModel()
) {
   Scaffold(
        content = { padding ->
//            Manager(
//            viewModel = viewModel,
//                padding = padding
//            ),
            ownedPanden(
                viewModel = viewModel,
                 padding = padding
            )
//            Adres(
//                viewModel = viewModel,
//                padding = padding
//            )
//            Issues(
//                issuesContent = { issues ->
//                    IssuesContent(
//                        padding = padding,
//                        issues = issues
//                    )
//                }
//            )
//            Adreses(
//                adresesContent = { adreses ->
//                    AdresesContent(
//                        padding = padding,
//                        adreses = adreses
//                    )
//                }
//            )
        }
    )
}
//
//
//
//
@Composable
fun ownedPanden(
    viewModel: AdresTestVM = hiltViewModel(),
    padding: PaddingValues
) {
//    viewModel.getIssuesForRoom("QTx6rzIOf8Y5G1KQQPUB", "2gLemNlYgYghm7InAZ")
    when(val issuesResponse = viewModel.rentedRoomsResponse) {
        is Response.Success -> Text(text = issuesResponse.data.size.toString())
        is Response.Failure -> Text(text = "very sad")
        Response.Loading -> Text(text = "loading")
    }
}
//}
//
//
//
//
//
//@Composable
//fun Adres(
//    viewModel: AdresTestVM = hiltViewModel(),
//    padding : PaddingValues
//) {
//    when(val adresResponse = viewModel.adresResponse) {
//        is Response.Loading -> Text(text = "Loading")
//        is Response.Success -> Text(text = adresResponse.data?.straat + "hey")
//        is Response.Failure -> print(adresResponse.e)
//    }
//}
//@Composable
//fun Manager(
//    viewModel: AdresTestVM = hiltViewModel(),
//    padding: PaddingValues
//) {
//    when(val managerResponse = viewModel.managerResponse) {
//        is Response.Loading -> Text(text = "Loading")
//        is Response.Success ->  Text(text = managerResponse.data?.voornaam + "hey")
//        is Response.Failure -> print(managerResponse.e)
//    }
//}
//
//
//@Composable
//fun Adreses(
//    viewModel: AdresTestVM = hiltViewModel(),
//    adresesContent: @Composable (adreses: Adreses) -> Unit
//) {
//    when(val adresesResponse = viewModel.adresesResponse) {
//        is Response.Loading -> Text(text = "Loading")
//        is Response.Success -> adresesContent(adresesResponse.data)
//        is Response.Failure -> print(adresesResponse.e)
//    }
//}
//
//@Composable
//fun AdresesContent(
//    padding: PaddingValues,
//    adreses: Adreses
//    ) {
//        Text(text = "Adressen:")
//        Text(text = "\n" + adreses[0].straat + " " + adreses[0].huisnummer + "\n")
//}
//
//
//
//@Composable
//fun Issues(
//    viewModel: AdresTestVM = hiltViewModel(),
//    issuesContent: @Composable (issues: Issues) -> Unit
//) {
//    when(val issuesResponse = viewModel.issuesForRoomResponse) {
//        is Response.Loading -> Text(text = "Loading")
//        is Response.Success -> issuesContent(issuesResponse.data)
//        is Response.Failure -> print(issuesResponse.e)
//    }
//}
//
//@Composable
//fun IssuesContent(
//    padding: PaddingValues,
//    issues: Issues
//) {
//    Text(text = "Issues:")
//    Text(text = "\n" + issues[0].titel + "\n")
//}



