package com.ugnet.sel1.presentation.adres

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.domain.models.Kamer
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.AdresesResponse
import com.ugnet.sel1.domain.repository.IssuesResponse
import com.ugnet.sel1.domain.repository.KamerResponse
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class AdresTestVM @Inject constructor(private val useCases : UseCases): ViewModel() {

      var adresesResponse by mutableStateOf<AdresesResponse>(Response.Loading)
          private set


      var kamerResponse by mutableStateOf<KamerResponse>(Response.Loading)
          private set

      var issuesResponse by mutableStateOf<IssuesResponse>(Response.Loading)
          private set


      init{
            getAdreses()
      }



      private fun getAdreses() = viewModelScope.launch {
            useCases.getAdreses().collect { response ->
                  adresesResponse = response
            }
      }

      private fun getIssuesByKamer(id : String) = viewModelScope.launch {
            useCases.getKamer(id).collect { response ->
                  kamerResponse = response
            }
            when (kamerResponse) {
                  is Response.Success -> {
                        val issues = (kamerResponse as Response.Success<Kamer?>).data?.issues

                        if (issues != null) {
                              useCases.getIssues(issues).collect { response ->
                                    issuesResponse = response
                              }
                        }
                  }
                  is Response.Failure -> Log.d("ERROR", "Error loading kamer")
                  Response.Loading -> wait()
            }
      }


      private fun getIssues(ids : List<String>) = viewModelScope.launch {
            useCases.getIssues(ids).collect { response ->
                  issuesResponse = response
            }
      }




//    private val data = MutableStateFlow(getAdresUseCase.invoke("LdIJ1hy6d7HOrGR8RiMS"))


//    fun refreshQuote() {
//        data.value = backend.get()
//    }
}