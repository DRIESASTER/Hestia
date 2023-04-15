package com.ugnet.sel1.presentation.adres

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.*
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdresTestVM @Inject constructor(private val useCases : UseCases): ViewModel() {

      var adresesResponse by mutableStateOf<AdresesResponse>(Response.Loading)
            private set

      var adresResponse by mutableStateOf<AdresResponse>(Response.Loading)
            private set


      var kamerResponse by mutableStateOf<KamerResponse>(Response.Loading)
          private set

      var issuesResponse by mutableStateOf<IssuesResponse>(Response.Loading)
          private set

      var kamersResponse by mutableStateOf<KamersResponse>(Response.Loading)
          private set

      var managerResponse by mutableStateOf<ManagerResponse>(Response.Loading)

      var pandenResponse by mutableStateOf<PandenResponse>(Response.Loading)


      init{
//            getAdreses()
////            getIssues(listOf("P1D7TlYRfKxa6Rd9pHqO "))
//            getManager(Firebase.auth.currentUser?.uid)
//            getAdres("LdIJ1hy6d7HOrGR8RiMS")
            getManager("4YNpPq1e3Gg2FTrnqPoW")
            getPanden()
      }


      fun getPanden() = viewModelScope.launch {
            useCases.getPanden().collect { response ->
                  pandenResponse = response
            }
      }


      private fun getManager(id : String) = viewModelScope.launch {
            useCases.getManager(id).collect { response ->
                  managerResponse = response
            }
      }


      private fun getAdreses() = viewModelScope.launch {
            useCases.getAdreses().collect { response ->
                  adresesResponse = response
            }
      }
//
      private fun getAdres(id : String) = viewModelScope.launch {
            useCases.getAdres(id).collect { response ->
                  adresResponse = response
            }
      }
//
//
//
//      private fun getIssuesByKamer2(id : String) = viewModelScope.launch {
//            managerUseCases.getKamer(id).collect { response ->
//                  kamerResponse = response
//            }
//            when (kamerResponse) {
//                  is Response.Success -> {
//                        val issues = (kamerResponse as Response.Success<Kamer?>).data?.issues
//
//                        if (issues != null) {
//                              getIssues(issues)
//                        }
//                  }
//                  is Response.Failure -> Log.d("ERROR", "Error loading kamer")
//                  Response.Loading -> wait()
//            }
//      }
//
//
//
//      fun getIssuesByKamer(id : String) = viewModelScope.launch {
//            managerUseCases.getKamer(id).collect { response ->
//                  kamerResponse = response
//            }
//            when (kamerResponse) {
//                  is Response.Success -> {
//                        val issues = (kamerResponse as Response.Success<Kamer?>).data?.issues
//
//                        if (issues != null) {
//                              managerUseCases.getIssues(issues).collect { response ->
//                                    issuesResponse = response
//                              }
//                        }
//                  }
//                  is Response.Failure -> Log.d("ERROR", "Error loading kamer")
//                  Response.Loading -> wait()
//            }
//      }
//
//
//      private fun getKamers() = viewModelScope.launch {
//            managerUseCases.getKamers().collect { response ->
//                  kamersResponse = response
//            }
//      }
//
//
//      private fun getIssues(ids : List<String>) = viewModelScope.launch {
//            managerUseCases.getIssues(ids).collect { response ->
//                  issuesResponse = response
//            }
//      }




//    private val data = MutableStateFlow(getAdresUseCase.invoke("LdIJ1hy6d7HOrGR8RiMS"))


//    fun refreshQuote() {
//        data.value = backend.get()
//    }
}