package com.ugnet.sel1.presentation.adres

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.AdresesResponse
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdresTestVM @Inject constructor(private val useCases : UseCases): ViewModel() {

      var adresesResponse by mutableStateOf<AdresesResponse>(Response.Loading)
            private set

      private fun getAdreses() = viewModelScope.launch {
            useCases.getAdreses().collect { response ->
                  adresesResponse = response
            }
      }



//    private val data = MutableStateFlow(getAdresUseCase.invoke("LdIJ1hy6d7HOrGR8RiMS"))


//    fun refreshQuote() {
//        data.value = backend.get()
//    }
}