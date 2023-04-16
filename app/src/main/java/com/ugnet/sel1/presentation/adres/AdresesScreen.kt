package com.ugnet.sel1.presentation.adres

import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel

//@Composable
//fun AdresesScreen(
 //   viewModel: AdresTestVM = hiltViewModel()
//) {
//    viewModel.getIssuesByKamer("OwyLqQ6Dv3ENGciJg0gA ")
//   Scaffold(
//        content = { padding ->
//            Manager(
//            viewModel = viewModel,
//                padding = padding
//            ),
  //          ownedPanden(
   //             viewModel = viewModel,
        //         padding = padding
     //       )
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
//        }
//    )
//}
//
//
//
//
//@Composable
//fun ownedPanden(
//    viewModel: AdresTestVM = hiltViewModel(),
//    padding : PaddingValues
//) {
//    viewModel.getOwnedProperties("4YNpPq1e3Gg2FTrnqPoW")
//    when(val pandResponse = viewModel.ownedPropertiesResponse) {
//        is Response.Loading -> Text(text = "Loading")
//        is Response.Success ->  Text(text = pandResponse.data?.?.get(0)?.adres?.straat + "hey")
//        }
//        is Response.Failure -> print(pandResponse.e)
//    }
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



